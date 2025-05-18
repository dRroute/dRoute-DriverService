package com.droute.driverservice.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.droute.driverservice.dto.request.JourneyDetailsRequestDto;
import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.entity.JourneyPoints;
import com.droute.driverservice.feign.client.GoogleMapClient;
import com.droute.driverservice.repository.JourneyPointsRepository;
import com.droute.driverservice.utils.LatLng;
import com.droute.driverservice.utils.PolylineDecoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AsyncService {

    @Value("${google.maps.api.key}")
    private String apiKey;


    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private GoogleMapClient googleMapClient;

    @Autowired
    private JourneyDetailService journeyDetailService;

    @Autowired
    private JourneyPointsRepository journeyPointsRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getStateFromCoordinates(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("latlng", latitude + "," + longitude)
                .queryParam("key", apiKey)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            for (JsonNode result : results) {
                for (JsonNode component : result.path("address_components")) {
                    for (JsonNode type : component.path("types")) {
                        if ("administrative_area_level_1".equals(type.asText())) {
                            return component.path("long_name").asText(); // e.g., "Maharashtra"
                        }
                    }
                }
            }

            return "Unknown";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    @Async
    public void saveJourneyPointsAsync(JourneyDetailEntity journeyDetailEntity,
            JourneyDetailsRequestDto journeyDetail) {

        try {

            logger.info("Async started: " + Thread.currentThread().getName());

            String origin = journeyDetail.getJourneySource().getLatitude() + ","
                    + journeyDetail.getJourneySource().getLongitude();
            String destination = journeyDetail.getJourneyDestination().getLatitude() + ","
                    + journeyDetail.getJourneyDestination().getLongitude();

            String response = googleMapClient.getDirections(origin, destination, apiKey, "driving", "overview");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);

            String encodedPolyline = jsonResponse.path("routes").path(0).path("overview_polyline").path("points")
                    .asText();
            List<LatLng> polylinePoints = PolylineDecoder.decode(encodedPolyline); // List of [lat, lng]

            int totalPoints = polylinePoints.size();
            System.out.println("totalPoints = " + totalPoints);
            if (totalPoints == 0) {
                throw new IllegalStateException("No polyline points found in route");
            }

            // Select 15 points including start and end
            Set<Integer> selectedIndexes = new LinkedHashSet<>();
            int sampleSize = 15;

            for (int i = 0; i < sampleSize; i++) {
                int index = (int) Math.round(i * (totalPoints - 1) / (double) (sampleSize - 1));
                selectedIndexes.add(index);
            }

            List<JourneyPoints> journeyPointsList = new ArrayList<>();

            for (int i = 0; i < totalPoints; i++) {
                LatLng latLng = polylinePoints.get(i);
                double lat = latLng.latitude;
                double lng = latLng.longitude;

                String stateName = null;
                if (selectedIndexes.contains(i)) {
                    try {
                        // Thread.sleep(200); // Respect API limits

                        String latLngStr = lat + "," + lng;
                        String addressJson = googleMapClient.getAddress(latLngStr, apiKey);
                        JsonNode addressNode = objectMapper.readTree(addressJson);

                        JsonNode results = addressNode.path("results");
                        if (results.isArray() && results.size() > 0) {
                            for (JsonNode result : results) {
                                for (JsonNode comp : result.path("address_components")) {
                                    boolean isState = false;
                                    for (JsonNode type : comp.path("types")) {
                                        if ("administrative_area_level_3".equals(type.asText())) {
                                            stateName = comp.path("long_name").asText();

                                        }
                                        if ("administrative_area_level_2".equals(type.asText())) {
                                            stateName = comp.path("long_name").asText();

                                        }
                                        if ("administrative_area_level_1".equals(type.asText())) {
                                            stateName = comp.path("long_name").asText();
                                            isState = true;
                                            break;
                                        }
                                    }
                                    if (stateName != null && isState)
                                        break;

                                }
                                if (stateName != null)
                                    break;
                            }
                        } else {
                            System.out.println("No geocode result for point " + i + " - " + latLngStr);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                journeyPointsList.add(JourneyPoints.builder()
                        .journeyId(journeyDetailEntity.getJourneyId())
                        .latitude(lat)
                        .longitude(lng)
                        .stateName(stateName)
                        .build());
            }

            journeyPointsRepository.saveAll(journeyPointsList);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in saveJourneyPointsAsync: " + e.getMessage());
        }
        logger.info("Async finished: " + Thread.currentThread().getName());
    }

}
