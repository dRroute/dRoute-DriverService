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

import com.droute.driverservice.dto.CommonResponseDto;
import com.droute.driverservice.entity.RatingEntity;
import com.droute.driverservice.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<RatingEntity>> postRating(@RequestBody RatingEntity rating) {
        RatingEntity savedRating = ratingService.postRating(rating);
        CommonResponseDto<RatingEntity> crd = new CommonResponseDto<>("Rating created successfully", savedRating);
        return ResponseEntity.status(HttpStatus.CREATED).body(crd);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<CommonResponseDto<RatingEntity>> getRatingById(@PathVariable Long ratingId) {
        RatingEntity rating = ratingService.getRatingById(ratingId);
        CommonResponseDto<RatingEntity> crd = new CommonResponseDto<>("Rating fetched successfully", rating);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<CommonResponseDto<RatingEntity>> updateRatingById(@PathVariable Long ratingId, @RequestBody RatingEntity rating) {
        rating.setRatingId(ratingId);  // Ensure the correct ID is set for updating
        RatingEntity updatedRating = ratingService.updateRatingById(rating);
        CommonResponseDto<RatingEntity> crd = new CommonResponseDto<>("Rating updated successfully", updatedRating);
        return new ResponseEntity<>(crd, HttpStatus.OK);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteRatingById(@PathVariable Long ratingId) {
        ratingService.deleteRatingById(ratingId);
        CommonResponseDto<Void> crd = new CommonResponseDto<>("Rating deleted successfully", null);
        return new ResponseEntity<>(crd, HttpStatus.NO_CONTENT);
    }
}
