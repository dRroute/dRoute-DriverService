package com.droute.driverservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeoCodingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

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
}
