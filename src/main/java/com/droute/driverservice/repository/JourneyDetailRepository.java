package com.droute.driverservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.droute.driverservice.entity.JourneyDetailEntity;

public interface JourneyDetailRepository extends JpaRepository<JourneyDetailEntity, Long> {

    List<JourneyDetailEntity> findByDriverId(Long driverId);

    List<JourneyDetailEntity> findByStatusNotIn(List<String> status);

}
