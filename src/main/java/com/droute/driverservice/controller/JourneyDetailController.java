package com.droute.driverservice.controller;

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

import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.service.JourneyDetailService;

@RestController
@RequestMapping("/api/journey-details")
public class JourneyDetailController {

    @Autowired
    private JourneyDetailService journeyDetailService;
  

    @PostMapping
    public ResponseEntity<CommonResponseDto<JourneyDetailEntity>> postJourneyDetails(@RequestBody JourneyDetailEntity journeyDetail) {
    	
        JourneyDetailEntity savedJourneyDetail = journeyDetailService.postJourneyDetail(journeyDetail);
        CommonResponseDto<JourneyDetailEntity> crd = new CommonResponseDto<>("Journey details created successfully", savedJourneyDetail);
        return new ResponseEntity<>(crd, HttpStatus.CREATED);
    }

    @GetMapping("/{journeyId}")
    public ResponseEntity<CommonResponseDto<JourneyDetailEntity>> getJourneyDetailsById(@PathVariable Long journeyId) {
        JourneyDetailEntity journeyDetail = journeyDetailService.getJourneyDetailById(journeyId);
        CommonResponseDto<JourneyDetailEntity> crd = new CommonResponseDto<>("Journey details fetched successfully", journeyDetail);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    @PutMapping("/{journeyId}")
    public ResponseEntity<CommonResponseDto<JourneyDetailEntity>> updateJourneyDetailsById(@PathVariable Long journeyId, @RequestBody JourneyDetailEntity journeyDetail) {
        journeyDetail.setJourneyId(journeyId);  // Ensure the correct ID is set for updating
        JourneyDetailEntity updatedJourneyDetail = journeyDetailService.updateJourneyDetailById(journeyDetail);
        CommonResponseDto<JourneyDetailEntity> crd = new CommonResponseDto<>("Journey details updated successfully", updatedJourneyDetail);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    @DeleteMapping("/{journeyId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteJourneyDetailsById(@PathVariable Long journeyId) {
        journeyDetailService.deleteJourneyDetailById(journeyId);
        CommonResponseDto<Void> crd = new CommonResponseDto<>("Journey details deleted successfully", null);
        return new ResponseEntity<>(crd, HttpStatus.NO_CONTENT);
    }
}
