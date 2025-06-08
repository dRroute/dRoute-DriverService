package com.droute.driverservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.ResponseBuilder;
import com.droute.driverservice.entity.RatingEntity;
import com.droute.driverservice.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public <T> ResponseEntity<CommonResponseDto<RatingEntity>> postRating(@RequestBody RatingEntity rating) {
        RatingEntity savedRating = ratingService.postRating(rating);
        return ResponseBuilder.success(HttpStatus.CREATED, "Rating created successfully", savedRating);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<CommonResponseDto<RatingEntity>> getRatingById(@PathVariable Long ratingId) {
        RatingEntity rating = ratingService.getRatingById(ratingId);
        return ResponseBuilder.success(HttpStatus.OK, "Rating fetched successfully", rating);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<CommonResponseDto<RatingEntity>> updateRatingById(@PathVariable Long ratingId, @RequestBody RatingEntity rating) {
        rating.setRatingId(ratingId);  // Ensure the correct ID is set for updating
        RatingEntity updatedRating = ratingService.updateRatingById(rating);
        return ResponseBuilder.success(HttpStatus.OK, "Rating updated successfully", updatedRating);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteRatingById(@PathVariable Long ratingId) {
        ratingService.deleteRatingById(ratingId);
        return ResponseBuilder.success(HttpStatus.OK, "Rating deleted successfully", null);
    }
}
