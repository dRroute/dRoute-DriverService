package com.droute.driverservice.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="driver_entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private String rcNumber;

    
    @OneToMany(mappedBy = "ratingId", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;
    
    // Account Details
    private String accountHolderName;
    private String driverBankName;
    private String driverAccountNo;
    private String driverIfsc;
    private String driverUpiId;
    private String aadharNumber;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id") // Foreign key in DocumentEntity table
    private Set<DocumentEntity> documents;
}