package com.droute.driverservice.entity;

import java.time.LocalDateTime;

import com.droute.driverservice.enums.JourneyStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JourneyDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journeyId;
    
    private Long driverId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journey_source_id", referencedColumnName = "locationId")
//    @JsonIgnore
    private LocationDetailsEntity journeySource;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journey_destination_id", referencedColumnName = "locationId")
//    @JsonIgnore
    private LocationDetailsEntity journeyDestination;
    
    private String visitedStateDuringJourney;
    private Double availableLength;
    private Double availableWidth;
    private Double availableHeight;
    private String availableSpaceMeasurementType;
    
    @Enumerated(EnumType.STRING)
    private JourneyStatus  status;
    
    private LocalDateTime expectedDepartureDateTime;
    private LocalDateTime expectedArrivalDateTime;
    
	
}

