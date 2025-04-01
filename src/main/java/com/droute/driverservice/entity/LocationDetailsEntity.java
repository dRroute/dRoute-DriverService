package com.droute.driverservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String longitude;
    private String latitude;
    
    private String address;
    
    private String city;
    private String pinode;
    private String country;

////    @OneToMany(mappedBy = "journeySource")
//    private List<JourneyDetailEntity> sourceJourneys;
//    
////    @OneToMany(mappedBy = "journeyDestination")
//    private List<JourneyDetailEntity> destinationJourneys;

    // Constructor
    
    
   
}

