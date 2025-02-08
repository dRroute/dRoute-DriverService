package com.droute.driverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.entity.RatingEntity;
import com.droute.driverservice.repository.RatingRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public RatingEntity postRating(RatingEntity rating) {
        return ratingRepository.save(rating);
    }

    public RatingEntity getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found with given id"));
    }

    public RatingEntity updateRatingById(RatingEntity rating) {
        getRatingById(rating.getRatingId()); // Check if the rating exists
        return ratingRepository.save(rating);
    }

    public void deleteRatingById(Long ratingId) {
        getRatingById(ratingId); // Check if the rating exists
        ratingRepository.deleteById(ratingId);
    }
}

