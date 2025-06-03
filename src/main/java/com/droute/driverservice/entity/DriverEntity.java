package com.droute.driverservice.entity;

import java.util.List;
import java.util.Set;

import com.droute.driverservice.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="driver_entity")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"ratings", "documents"})
@Builder
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    
    private Long driverDetailsId; // Reference to User in another microservice
    
    // Driver Vehicle Details
    private String vehicleNumber;
    private String drivingLicenceNo;
    private String vehicleName;
    private String vehicleType;

    @OneToMany(mappedBy = "ratingId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RatingEntity> ratings;
    
    // Account Details
    private String accountHolderName;
    private String driverBankName;
    private String driverAccountNo;
    private String driverIfsc;
    private String driverUpiId;
    private String aadharNumber;
    
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DocumentEntity> documents;

    @Enumerated(EnumType.STRING )
    private ProfileStatus profileStatus; // Enum for ACTIVE, INACTIVE, BLOCKED, etc.
    
}