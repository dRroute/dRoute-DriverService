package com.droute.driverservice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.droute.driverservice.entity.DocumentEntity;

@Repository
public interface DocumentEntityRepository extends JpaRepository<DocumentEntity, Long> {

    // Corrected method for finding documents by driverId
    Set<DocumentEntity> findByDriver_DriverId(Long driverId);  

    // Corrected method for deleting documents by driverId
    void deleteByDriver_DriverId(Long driverId);  
}
