package com.droute.driverservice.dto.request;

import java.time.LocalDateTime;

import com.droute.driverservice.entity.LocationDetailsEntity;
import com.droute.driverservice.enums.JourneyStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JourneyDetailsRequestDto {
    private Long driverId;

    private LoctionDetailsRequestDto journeySource;

    private LoctionDetailsRequestDto journeyDestination;

    private Double availableLength;
    private Double availableWidth;
    private Double availableHeight;
    private String availableSpaceMeasurementType;

    private JourneyStatus status;

    private LocalDateTime expectedDepartureDateTime;
    private LocalDateTime expectedArrivalDateTime;
}
