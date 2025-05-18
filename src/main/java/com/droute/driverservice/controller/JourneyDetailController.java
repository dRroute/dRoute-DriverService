package com.droute.driverservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.droute.driverservice.dto.request.CourierDetailResponseDto;
import com.droute.driverservice.dto.request.JourneyDetailsRequestDto;
import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.ResponseBuilder;
import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.service.JourneyDetailService;
import com.droute.driverservice.service.JourneyPointsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/driver/journey-details")
public class JourneyDetailController {

    private static final Logger logger = LoggerFactory.getLogger(JourneyDetailController.class);

    @Autowired
    private JourneyDetailService journeyDetailService;
    @Autowired
    private JourneyPointsService journeyPointsService;

    @PostMapping("")
    public ResponseEntity<CommonResponseDto<JourneyDetailEntity>> postJourneyDetails(
            @Valid @RequestBody JourneyDetailsRequestDto journeyDetail) {

                logger.info("Journey details = {}", journeyDetail);

        JourneyDetailEntity savedJourneyDetail = journeyPointsService.saveJourneyAndPoints(journeyDetail);
        return ResponseBuilder.success(HttpStatus.CREATED, "Journey details created successfully", savedJourneyDetail);
    }
    // @PostMapping("/postJourneyDetailsPoints")
    // public ResponseEntity<CommonResponseDto<JsonNode>> postJourneyDetailsPoints(
    //         @Valid @RequestBody JourneyDetailsRequestDto journeyDetail) {

    //             logger.info("Journey details = {}", journeyDetail);

    //     var savedJourneyDetail = journeyPointsService.saveJourneyAndPoints(journeyDetail);
    //     return ResponseBuilder.success(HttpStatus.CREATED, "Journey details created successfully", savedJourneyDetail);
    // }

    @GetMapping("/{journeyId}")
    public ResponseEntity<CommonResponseDto<JourneyDetailEntity>> getJourneyDetailsById(@PathVariable Long journeyId) {
        JourneyDetailEntity journeyDetail = journeyDetailService.getJourneyDetailById(journeyId);
        return ResponseBuilder.success(HttpStatus.OK, "Journey details fetched successfully", journeyDetail);
    }

    @PutMapping("")
    public ResponseEntity<CommonResponseDto<JourneyDetailEntity>> updateJourneyDetailsById(
            @RequestBody JourneyDetailEntity journeyDetail) {
        JourneyDetailEntity updatedJourneyDetail = journeyDetailService.updateJourneyDetailById(journeyDetail);
        return ResponseBuilder.success(HttpStatus.OK, "Journey details updated successfully", updatedJourneyDetail);
    }

    @DeleteMapping("/{journeyId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteJourneyDetailsById(@PathVariable Long journeyId) {
        journeyDetailService.deleteJourneyDetailById(journeyId);
        return ResponseBuilder.success(HttpStatus.OK, "Journey details deleted successfully", null);
    }

    // Get journey list by filter with courier details

    @PostMapping("/filter")
    public String getJourneysByCourierConditions(@RequestBody CourierDetailResponseDto courierDetails) {
        
        logger.info("courier details in driver = {}", courierDetails);
        return "Success";
    }

}
