package com.droute.driverservice.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.droute.driverservice.dto.request.RequestDriverProfileDetailsDto;
import com.droute.driverservice.dto.request.ResetPasswordRequestDTO;
import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.CompleteDriverDetailsResponseDto;
import com.droute.driverservice.dto.response.ResponseBuilder;
import com.droute.driverservice.entity.DriverEntity;
import com.droute.driverservice.exception.EntityAlreadyExistsException;

import com.droute.driverservice.service.DriverEntityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private DriverEntityService driveEntityService;

    @Autowired
    private DriverEntityService driverService;

    @PostMapping("/profile-complete")
    public ResponseEntity<CommonResponseDto<DriverEntity>> createDriver(
            @Valid @RequestBody RequestDriverProfileDetailsDto driverProfileDetails)
            throws EntityAlreadyExistsException {
        DriverEntity savedDriver = driveEntityService.completeDriverProfile(driverProfileDetails);
        return ResponseBuilder.success(HttpStatus.CREATED, "Driver Details Saved Successfully.", savedDriver);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<CommonResponseDto<CompleteDriverDetailsResponseDto>> getDriverById(@PathVariable Long driverId) {
        var driverDetails = driveEntityService.getDriverById(driverId);
        return ResponseBuilder.success(HttpStatus.OK, "Driver Details Found Successfully", driverDetails);
    }

@PatchMapping("/reset-password")
    public ResponseEntity<CommonResponseDto<String>> resetPassword(@RequestBody ResetPasswordRequestDTO requestDto) {
        var response =  driverService.resetDriverPassword(requestDto);
        return ResponseBuilder.success(HttpStatus.OK, "Password reset successfully.", response.getData());
    }

    // @PutMapping("/{driverId}")
    // public ResponseEntity<CompleteDriverDetailsResponseDto> updateDriver(
    //         @PathVariable Long driverId, 
    //         @RequestBody CompleteDriverDetailsResponseDto updateRequest) {
        
    //     CompleteDriverDetailsResponseDto updatedDriver = driverService.updateDriverById(driverId, updateRequest);
        
    //     return ResponseEntity.ok(updatedDriver);
    // }



    /*
     * @GetMapping
     * public ResponseEntity<List<DriverEntity>> getAllDrivers() {
     * return ResponseEntity.ok(driverRepository.findAll());
     * }
     * 
     * 
     * 
     * @DeleteMapping("/{id}")
     * public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
     * return driverRepository.findById(id)
     * .map(driver -> {
     * driverRepository.delete(driver);
     * return ResponseEntity.noContent().build();
     * })
     * .orElse(ResponseEntity.notFound().build());
     * }
     * 
     */
}
