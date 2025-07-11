package com.droute.driverservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.droute.driverservice.entity.DriverEntity;

@Repository
public interface DriverEntityRepository extends JpaRepository<DriverEntity, Long> {

	DriverEntity findByDriverDetailsId(Long userId);

	boolean existsByDriverDetailsId(Long userId);

	Optional<DriverEntity> findByDriverId(Long driverId);
}

