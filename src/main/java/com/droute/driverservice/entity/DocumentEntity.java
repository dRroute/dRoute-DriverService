package com.droute.driverservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="document_entity")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "driver") // Exclude driver to avoid circular reference in toString
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    
    private String documentName;
    private String documentType;
    private String documentUrl;
    
    @ManyToOne
    @JoinColumn(name = "driver_id")  // This creates a foreign key reference to DriverEntity
    @JsonIgnore // Prevents circular reference during serialization
    private DriverEntity driver;
    // No need to add a reference to DriverEntity here
}