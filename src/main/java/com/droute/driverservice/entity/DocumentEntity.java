package com.droute.driverservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="document_entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    
    private String documentName;
    private String documentType;
    private String documentUrl;
    
    @ManyToOne
    @JoinColumn(name = "driver_id")  // This creates a foreign key reference to DriverEntity
    private DriverEntity driver;
    // No need to add a reference to DriverEntity here
}