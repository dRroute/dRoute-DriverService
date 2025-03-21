package com.droute.driverservice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.droute.driverservice.entity.DocumentEntity;

@Repository
public interface DocumentEntityRepository extends JpaRepository<DocumentEntity, Long>{

    void deleteByDriverId(Long driverId);

    Set<DocumentEntity> findByDriverId(Long driverId);

	

	
}
