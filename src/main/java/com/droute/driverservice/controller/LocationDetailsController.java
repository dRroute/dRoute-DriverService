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
import com.droute.driverservice.dto.response.ResponseBuilder;
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
        return ResponseBuilder.success(HttpStatus.CREATED,"Location details created successfully",savedLocation);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<LocationDetailsEntity>> getLocationDetailsById(@PathVariable Long locationId) {
        LocationDetailsEntity location = locationDetailsService.getLocationDetailsById(locationId);
        return ResponseBuilder.success(HttpStatus.OK,"Location details fetched successfully", location);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<LocationDetailsEntity>> updateLocationDetailsById( @RequestBody LocationDetailsEntity locationDetails) {
        LocationDetailsEntity updatedLocation = locationDetailsService.updateLocationDetailsById(locationDetails);
        return ResponseBuilder.success(HttpStatus.OK, "Location details updated successfully", updatedLocation );
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteLocationDetailsById(@PathVariable Long locationId) {
        locationDetailsService.deleteLocationDetailsById(locationId);
        return ResponseBuilder.success(HttpStatus.OK, "Location details deleted successfully", null );
    }
}
