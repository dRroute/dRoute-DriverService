package com.droute.driverservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.droute.driverservice.dto.CommonResponseDto;
import com.droute.driverservice.dto.RequestDriverProfileDetailsDto;
import com.droute.driverservice.entity.DriverEntity;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.service.DriverEntityService;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

	@Autowired
	private DriverEntityService driveEntityService;

   
    @PostMapping("/profile-complete")
    public ResponseEntity<CommonResponseDto<DriverEntity>> createDriver(@RequestBody RequestDriverProfileDetailsDto driverProfileDetails) throws EntityAlreadyExistsException {
    	DriverEntity savedDriver = driveEntityService.completeDriverProfile(driverProfileDetails);
    	var crd = new CommonResponseDto<>("Driver founded Successfully.", savedDriver);
        return ResponseEntity.status(HttpStatus.CREATED).body(crd);
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<DriverEntity> getDriverById(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DriverEntity>> getAllDrivers() {
        return ResponseEntity.ok(driverRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverEntity> updateDriver(@PathVariable Long id, @RequestBody Driver driverDetails) {
        return driverRepository.findById(id)
                .map(driver -> {
                    driver.setDriverDetailsId(driverDetails.getDriverDetailsId());
                    driver.setVehicleNumber(driverDetails.getVehicleNumber());
                    driver.setDrivingLicenceNo(driverDetails.getDrivingLicenceNo());
                    driver.setVehicleName(driverDetails.getVehicleName());
                    driver.setVehicleType(driverDetails.getVehicleType());
                    driver.setRatings(driverDetails.getRatings());
                    driver.setDriverAccountNo(driverDetails.getDriverAccountNo());
                    driver.setDriverIfsc(driverDetails.getDriverIfsc());
                    driver.setDriverUpiId(driverDetails.getDriverUpiId());
                    return ResponseEntity.ok(driverRepository.save(driver));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(driver -> {
                    driverRepository.delete(driver);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    */
}

