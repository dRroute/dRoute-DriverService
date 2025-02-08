package com.droute.driverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.droute.driverservice.entity.RatingEntity;

public interface RatingRepository extends  JpaRepository<RatingEntity, Long> {

	RatingEntity findByRatingId(Long ratingId);

	void deleteByRatingId(Long ratingId);

}
