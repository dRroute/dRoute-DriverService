package com.droute.driverservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.droute.driverservice.controller.JourneyDetailController;
import com.droute.driverservice.dto.request.JourneyDetailsRequestDto;
import com.droute.driverservice.entity.JourneyDetailEntity;

@Service
public class JourneyPointsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final Logger logger = LoggerFactory.getLogger(JourneyDetailController.class);

    @Autowired
    private JourneyDetailService journeyDetailService;

    @Autowired
    private AsyncService asyncService;

    /*
     * public JourneyDetailEntity saveJourneyAndPoints(JourneyDetailsRequestDto
     * journeyDetail) {
     * 
     * var journeyDetailEntity =
     * journeyDetailService.postJourneyDetail(journeyDetail);
     * String origin = journeyDetail.getJourneySource().getLatitude() + ","
     * + journeyDetail.getJourneySource().getLongitude();
     * String destination = journeyDetail.getJourneyDestination().getLatitude() +
     * ","
     * + journeyDetail.getJourneyDestination().getLongitude();
     * 
     * try {
     * // Fetch directions JSON string
     * String response = googleMapClient.getDirections(origin, destination, apiKey,
     * "driving", "overview");
     * 
     * // Convert response to JSON
     * ObjectMapper objectMapper = new ObjectMapper();
     * JsonNode jsonResponse = objectMapper.readTree(response);
     * 
     * List<JourneyPoints> journeyPointsList = new ArrayList<>();
     * 
     * JsonNode steps =
     * jsonResponse.path("routes").path(0).path("legs").path(0).path("steps");
     * int totalSteps = steps.size();
     * 
     * if (totalSteps == 0) {
     * throw new IllegalStateException("No steps found in route");
     * }
     * 
     * // Compute the 10 indexes to assign stateName via reverse geocoding
     * (including
     * // source and destination)
     * Set<Integer> selectedIndexes = new LinkedHashSet<>();
     * 
     * // Always include start and end
     * selectedIndexes.add(0);
     * selectedIndexes.add(totalSteps - 1);
     * 
     * // Now add 8 equidistant points in between (excluding start and end)
     * int intervalCount = 30; // 10 - 1 (start) - 1 (end)
     * for (int i = 1; i <= intervalCount; i++) {
     * int index = (int) Math.round(i * (totalSteps - 1) / intervalCount);
     * selectedIndexes.add(index); // Safe add, Set avoids duplication
     * }
     * 
     * System.out.println("Selected indexes for state mapping: " + selectedIndexes);
     * System.out.println("length of steps: " + totalSteps);
     * 
     * for (int i = 0; i < totalSteps; i++) {
     * JsonNode step = steps.get(i);
     * JsonNode startLocation = step.path("start_location");
     * double lat = startLocation.path("lat").asDouble();
     * double lng = startLocation.path("lng").asDouble();
     * 
     * String stateName = null;
     * System.out.println("i = " + i);
     * if (selectedIndexes.contains(i)) {
     * try {
     * String latLng = lat + "," + lng;
     * String addressJson = googleMapClient.getAddress(latLng, apiKey);
     * ObjectMapper mapper = new ObjectMapper();
     * JsonNode addressNode = mapper.readTree(addressJson);
     * 
     * JsonNode results = addressNode.path("results");
     * if (results.isMissingNode() || !results.isArray() || results.size() == 0) {
     * System.out.println("No geocode result for index " + i + ", latLng: " + lat +
     * "," + lng);
     * continue;
     * }
     * 
     * if (results.isArray() && results.size() > 0) {
     * 
     * System.out.println("results for i in address = " + i);
     * for (JsonNode result : results) {
     * JsonNode components = result.path("address_components");
     * for (JsonNode comp : components) {
     * System.out.println("comp for i =" + i + " = " + comp);
     * JsonNode types = comp.path("types");
     * if (types.isArray()) {
     * for (JsonNode type : types) {
     * if (type.asText().equals("administrative_area_level_1")) {
     * stateName = comp.path("long_name").asText();
     * break;
     * }
     * }
     * }
     * if (stateName != null)
     * break;
     * }
     * if (stateName != null)
     * break;
     * 
     * }
     * }
     * 
     * } catch (Exception ex) {
     * ex.printStackTrace();
     * }
     * }
     * 
     * JourneyPoints point = JourneyPoints.builder()
     * .journeyId(journeyDetailEntity.getJourneyId())
     * .latitude(lat)
     * .longitude(lng)
     * .stateName(stateName) // could be null if not in selectedIndexes or failed
     * reverse geocoding
     * .build();
     * 
     * journeyPointsList.add(point);
     * }
     * 
     * journeyPointsRepository.saveAll(journeyPointsList);
     * 
     * } catch (Exception e) {
     * e.printStackTrace();
     * return null;
     * }
     * 
     * return journeyDetailEntity;
     * }
     */

    public JourneyDetailEntity saveJourneyAndPoints(JourneyDetailsRequestDto journeyDetail) {

        var journeyDetailEntity = journeyDetailService.postJourneyDetail(journeyDetail);

        // Save journey points asynchronously
        // This will not block the main thread
        logger.info("Saving journey points asynchronously for journeyId: {}", journeyDetailEntity.getJourneyId());
        asyncService.saveJourneyPointsAsync(journeyDetailEntity, journeyDetail);

        return journeyDetailEntity;
    }
    


}
