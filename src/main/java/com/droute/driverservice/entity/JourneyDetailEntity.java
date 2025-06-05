package com.droute.driverservice.entity;

import java.time.LocalDateTime;

import com.droute.driverservice.enums.DimensionUnit;
import com.droute.driverservice.enums.JourneyStatus;
import com.droute.driverservice.enums.WeightUnit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    @Enumerated(EnumType.STRING)
    private DimensionUnit availableSpaceMeasurementType;

    private Double availableWeight;

    @Enumerated(EnumType.STRING)
    private WeightUnit availableWeightMeasurementType;
    
    @Enumerated(EnumType.STRING)
    private JourneyStatus  status;

    @Column(name = "total_confirmed_packages")
    @Builder.Default
    private Long totalConfirmedPackages = 0L;
    
    private LocalDateTime expectedDepartureDateTime;
    private LocalDateTime expectedArrivalDateTime;
    
	
}

