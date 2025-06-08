package com.droute.driverservice.dto.response;

import com.droute.driverservice.entity.LocationDetailsEntity;
import com.droute.driverservice.enums.DimensionUnit;
import com.droute.driverservice.enums.WeightUnit;
import com.droute.driverservice.enums.JourneyStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JourneyDetailsResponseDto {
    private Long journeyId;
    private Long driverId;
    private LocationDetailsEntity journeySource;
    private LocationDetailsEntity journeyDestination;
    private String visitedStateDuringJourney;
    private Double availableLength;
    private Double availableWidth;
    private Double availableHeight;
    private DimensionUnit availableSpaceMeasurementType;
    private Double availableWeight;
    private WeightUnit availableWeightMeasurementType;
    private JourneyStatus status;
    private LocalDateTime expectedDepartureDateTime;
    private LocalDateTime expectedArrivalDateTime;
}