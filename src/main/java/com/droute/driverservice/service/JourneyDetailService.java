package com.droute.driverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.dto.request.JourneyDetailsRequestDto;
import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.entity.LocationDetailsEntity;
import com.droute.driverservice.repository.JourneyDetailRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JourneyDetailService {

    @Autowired
    private JourneyDetailRepository journeyDetailRepository;

    @Autowired
    private DriverEntityService driverEntityService;

    public JourneyDetailEntity postJourneyDetail(JourneyDetailsRequestDto journeyDetail) {
        // Convert DTO to Entity

        if (!driverEntityService.checkDriverExistByDriverId(journeyDetail.getDriverId())) {
            throw new EntityNotFoundException("Driver not found with id = "+journeyDetail.getDriverId());
            
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
}
