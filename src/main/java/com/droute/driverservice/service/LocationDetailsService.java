package com.droute.driverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.entity.LocationDetailsEntity;
import com.droute.driverservice.repository.LocationDetailsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LocationDetailsService {

    @Autowired
    private LocationDetailsRepository locationDetailsRepository;

    public LocationDetailsEntity postLocationDetails(LocationDetailsEntity locationDetails) {
        return locationDetailsRepository.save(locationDetails);
    }

    public LocationDetailsEntity getLocationDetailsById(Long locationId) {
        return locationDetailsRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with given id"));
    }

    public LocationDetailsEntity updateLocationDetailsById(LocationDetailsEntity locationDetails) {
        getLocationDetailsById(locationDetails.getLocationId()); // Ensure the location exists
        return locationDetailsRepository.save(locationDetails);
    }

    public void deleteLocationDetailsById(Long locationId) {
        getLocationDetailsById(locationId); // Ensure the location exists
        locationDetailsRepository.deleteById(locationId);
    }
}
