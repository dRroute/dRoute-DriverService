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
import com.droute.driverservice.entity.LocationDetailsEntity;
import com.droute.driverservice.service.LocationDetailsService;

@RestController
@RequestMapping("/api/location-details")
public class LocationDetailsController {

    @Autowired
    private LocationDetailsService locationDetailsService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<LocationDetailsEntity>> postLocationDetails(@RequestBody LocationDetailsEntity locationDetails) {
        LocationDetailsEntity savedLocation = locationDetailsService.postLocationDetails(locationDetails);
        CommonResponseDto<LocationDetailsEntity> crd = new CommonResponseDto<>("Location details created successfully", savedLocation);
        return new ResponseEntity<>(crd, HttpStatus.CREATED);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<LocationDetailsEntity>> getLocationDetailsById(@PathVariable Long locationId) {
        LocationDetailsEntity location = locationDetailsService.getLocationDetailsById(locationId);
        CommonResponseDto<LocationDetailsEntity> crd = new CommonResponseDto<>("Location details fetched successfully", location);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<LocationDetailsEntity>> updateLocationDetailsById(@PathVariable Long locationId, @RequestBody LocationDetailsEntity locationDetails) {
        locationDetails.setLocationId(locationId);  // Ensure the correct ID is set for updating
        LocationDetailsEntity updatedLocation = locationDetailsService.updateLocationDetailsById(locationDetails);
        CommonResponseDto<LocationDetailsEntity> crd = new CommonResponseDto<>("Location details updated successfully", updatedLocation);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteLocationDetailsById(@PathVariable Long locationId) {
        locationDetailsService.deleteLocationDetailsById(locationId);
        CommonResponseDto<Void> crd = new CommonResponseDto<>("Location details deleted successfully", null);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }
}
