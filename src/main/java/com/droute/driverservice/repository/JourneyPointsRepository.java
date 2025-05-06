package com.droute.driverservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.droute.driverservice.entity.JourneyPoints;

public interface JourneyPointsRepository extends JpaRepository<JourneyPoints, Long> {
    List<JourneyPoints> findByJourneyId(Long journeyId);
}
