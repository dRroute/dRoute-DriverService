package com.droute.driverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.droute.driverservice.entity.LocationDetailsEntity;

@Repository
public interface LocationDetailsRepository extends JpaRepository<LocationDetailsEntity, Long>{

	LocationDetailsEntity findByLocationId(Long locationId);

	void deleteByLocationId(Long locationId);

	
}
