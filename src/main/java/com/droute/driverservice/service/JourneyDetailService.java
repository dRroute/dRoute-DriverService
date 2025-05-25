package com.droute.driverservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.droute.driverservice.dto.request.JourneyDetailsRequestDto;
import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.entity.JourneyPoints;
import com.droute.driverservice.entity.LocationDetailsEntity;
import com.droute.driverservice.feign.client.GoogleMapClient;
import com.droute.driverservice.repository.JourneyDetailRepository;
import com.droute.driverservice.repository.JourneyPointsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JourneyDetailService {

    @Autowired
    private JourneyDetailRepository journeyDetailRepository;

    @Autowired
    private DriverEntityService driverEntityService;

    @Autowired
    private GoogleMapClient googleMapClient;

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Autowired
    private JourneyPointsRepository journeyPointsRepository;

    public JourneyDetailEntity postJourneyDetail(JourneyDetailsRequestDto journeyDetail) {
        // Convert DTO to Entity

        if (!driverEntityService.checkDriverExistByDriverId(journeyDetail.getDriverId())) {
            throw new EntityNotFoundException("Driver not found with id = " + journeyDetail.getDriverId());

        }

        var sourceLocation = LocationDetailsEntity.builder()
                .longitude(journeyDetail.getJourneySource().getLongitude())
                .latitude(journeyDetail.getJourneySource().getLatitude())
                .address(journeyDetail.getJourneySource().getAddress())
                .city(journeyDetail.getJourneySource().getCity())
                .pinode(journeyDetail.getJourneySource().getPinode())
                .country(journeyDetail.getJourneySource().getCountry())
                .build();

        var destinationLocation = LocationDetailsEntity.builder()
                .longitude(journeyDetail.getJourneyDestination().getLongitude())
                .latitude(journeyDetail.getJourneyDestination().getLatitude())
                .address(journeyDetail.getJourneyDestination().getAddress())
                .city(journeyDetail.getJourneyDestination().getCity())
                .pinode(journeyDetail.getJourneyDestination().getPinode())
                .country(journeyDetail.getJourneyDestination().getCountry())
                .build();

        JourneyDetailEntity journeyDetailEntity = JourneyDetailEntity.builder()

                .driverId(journeyDetail.getDriverId())
                .journeySource(sourceLocation)
                .journeyDestination(destinationLocation)
                .visitedStateDuringJourney(journeyDetail.getVisitedStateDuringJourney())
                .availableHeight(journeyDetail.getAvailableHeight())
                .availableLength(journeyDetail.getAvailableLength())
                .availableWidth(journeyDetail.getAvailableWidth())
                .availableSpaceMeasurementType(journeyDetail.getAvailableSpaceMeasurementType())
                .status(journeyDetail.getStatus())
                .expectedDepartureDateTime(journeyDetail.getExpectedDepartureDateTime())
                .expectedArrivalDateTime(journeyDetail.getExpectedArrivalDateTime())
                .build();
        return journeyDetailRepository.save(journeyDetailEntity);
    }

    public JourneyDetailEntity getJourneyDetailById(Long journeyId) {
        return journeyDetailRepository.findById(journeyId)
                .orElseThrow(() -> new EntityNotFoundException("Journey not found with given id"));
    }

    public boolean journeyExistsById(Long journeyId) {
        return journeyDetailRepository.existsById(journeyId);
    }

    public JourneyDetailEntity updateJourneyDetailById(JourneyDetailEntity journeyDetail) {
        if (getJourneyDetailById(journeyDetail.getJourneyId()) == null) {
            throw new EntityNotFoundException("Journey not found with given id");
        }

        return journeyDetailRepository.save(journeyDetail);
    }

    public void deleteJourneyDetailById(Long journeyId) {
        JourneyDetailEntity journeyDetail = getJourneyDetailById(journeyId);
        if (journeyDetail == null) {
            throw new EntityNotFoundException("Journey not found with given id");
        }

        journeyDetailRepository.deleteById(journeyId);
    }

    public List<JourneyDetailEntity> getJourneyDetailByDriverId(Long driverId) {
        if (!driverEntityService.checkDriverExistByDriverId(driverId)) {
            throw new EntityNotFoundException("Driver not found with id = " + driverId);

        }
        return journeyDetailRepository.findByDriverId(driverId);
    }

    public List<JourneyDetailEntity> getJourneyDetailByValidStatus() {
        List<String> excludedStatuses = Arrays.asList("COMPLETED", "CANCELLED");

        return journeyDetailRepository.findByStatusNotIn(excludedStatuses);
    }

    public List<JourneyDetailEntity> filterJourneyByState(String courierSourceCoordinate,
            String courierDestinationCoordinate, List<JourneyDetailEntity> journeyDetails)
            throws JsonMappingException, JsonProcessingException {
        // Get courier source and destination state from coordinates
        String courierSourceState = getStateFromCoordinates(courierSourceCoordinate);
        String courierDestinationState = getStateFromCoordinates(courierDestinationCoordinate);
        if (courierSourceState == null || courierDestinationState == null) {
            throw new IllegalArgumentException("Invalid coordinates provided");
        }

        // Filter journey details based on the states
        // Check if the visited states contain either the source or destination state
        var filteredJourneyDetails = journeyDetails.stream()
                .filter(journeyDetail -> {
                    String[] visitedStates = journeyDetail.getVisitedStateDuringJourney().split(",");
                    return Arrays.asList(visitedStates).contains(courierSourceState)
                            && Arrays.asList(visitedStates).contains(courierDestinationState);
                })
                .toList();

        if (filteredJourneyDetails.isEmpty()) {
            throw new EntityNotFoundException("No journeys found for the given source and destination states");
        }

        List<JourneyDetailEntity> result = new ArrayList<>();
        filteredJourneyDetails.forEach(journeyDetail -> {
            List<JourneyPoints> jouneyPoints = journeyPointsRepository.findByJourneyId(journeyDetail.getJourneyId());
            int[] indexRange = getIndexRange(jouneyPoints, courierSourceState, courierDestinationState);

            boolean flag = false;
            // calculate the distance for Courier Source Coordinate
            for (int i = indexRange[0]; i <= indexRange[1]; i++) {
                JourneyPoints point = jouneyPoints.get(i);
                // Apply haversine formula to calculate distance
                String[] srcCoords = courierSourceCoordinate.split(",");
                double srcLat = Double.parseDouble(srcCoords[0]);
                double srcLng = Double.parseDouble(srcCoords[1]);
                double distance = haversine(srcLat, srcLng, point.getLatitude(), point.getLongitude());
                // Use the distance as needed (e.g., print, store, compare, etc.)
                if (distance <= 50) {
                    flag = true;
                    break;
                }

            }
            if (flag) {
                for (int i = indexRange[2]; i <= indexRange[3]; i++) {
                    JourneyPoints point = jouneyPoints.get(i);
                    // Apply haversine formula to calculate distance
                    String[] destCoords = courierDestinationCoordinate.split(",");
                    double destLat = Double.parseDouble(destCoords[2]);
                    double destLng = Double.parseDouble(destCoords[3]);
                    double distance = haversine(destLat, destLng, point.getLatitude(), point.getLongitude());
                    // Use the distance as needed (e.g., print, store, compare, etc.)
                    if (distance <= 50) {
                        flag = true;
                        break;
                    }

                }
            }

            if (flag) {
                result.add(journeyDetail);
                
            }

        });

        return result;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // distance in kilometers
    }

    private int[] getIndexRange( List<JourneyPoints> journeyPoints, String sourceStateName, String destinationStateName)  {
        int sourceStartIndex = 0;
        int sourceEndIndex = 0;
        int destinationStartIndex = 0;
        int destinationEndIndex = 0;

        boolean foundSourceState = false;
        boolean foundDestinationState = false;
        for (int i = 0; i < journeyPoints.size(); i++) {
            JourneyPoints point = journeyPoints.get(i);
            String pointState = point.getStateName();
            if (!foundSourceState && pointState.equals(sourceStateName)) {     
                    sourceEndIndex = i;
                    foundSourceState = true;
    
            } else if (foundSourceState && pointState != null) {
                sourceEndIndex = i;
            } else if (!foundSourceState && pointState == null){
                sourceStartIndex = i;
                sourceEndIndex = i;
            }
            // Check for destination state
            if (!foundDestinationState && point.getStateName().equals(destinationStateName)) {
                destinationStartIndex = i;
                foundDestinationState = true;
            } else if (foundDestinationState && pointState != null) {
                destinationEndIndex = i;
                break;
            } else if(!foundDestinationState && pointState == null) {
                destinationStartIndex = i;
                destinationEndIndex = i;
            }
        }
        return new int[] { sourceStartIndex, sourceEndIndex , destinationStartIndex, destinationEndIndex };
    }

    private String getStateFromCoordinates(String latLng) throws JsonMappingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        String addressJson = googleMapClient.getAddress(latLng, apiKey);
        JsonNode addressNode = objectMapper.readTree(addressJson);
        String stateName = null;
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
            System.out.println("No geocode result for point " + latLng);
        }
        return stateName;
    }
}
