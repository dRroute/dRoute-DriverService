package com.droute.driverservice.dto.request;

import java.time.LocalDateTime;

import com.droute.driverservice.enums.JourneyStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
  
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString 
public class JourneyDetailsRequestDto {

    @NotNull(message = "Driver ID cannot be null")
    private Long driverId;

    @NotNull(message = "Journey source cannot be null")
    private LoctionDetailsRequestDto journeySource;

    @NotNull(message = "Journey destination cannot be null")
    private LoctionDetailsRequestDto journeyDestination;

    @NotNull(message = "Journey destination cannot be null")
    private String visitedStateDuringJourney;

    @Positive(message = "Available length must be positive")
    @NotNull(message = "Available Length cannot be null")
    private Double availableLength;

    @Positive(message = "Available width must be positive")
    @NotNull(message = "Available Width cannot be null")
    private Double availableWidth;

    @Positive(message = "Available height must be positive")
    @NotNull(message = "Available Height cannot be null")
    private Double availableHeight;

    @NotBlank(message = "Available space measurement type cannot be blank")
    @NotNull(message = "Available Space measurement type cannot be null")
    private String availableSpaceMeasurementType;

    @NotNull(message = "Journey status cannot be null")
    private JourneyStatus status;

    @NotNull(message = "Expected departure date and time cannot be null")
    @Future(message = "Expected departure date and time must be in the future")
    private LocalDateTime expectedDepartureDateTime;

    @NotNull(message = "Expected arrival date and time cannot be null")
    @Future(message = "Expected arrival date and time must be in the future")
    private LocalDateTime expectedArrivalDateTime;
}