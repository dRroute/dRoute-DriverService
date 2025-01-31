package com.droute.driverservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RatingEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ratingId;
	private Float star;
	private Long ratedDriverDetailsId;
	private Long ratedByUserId;
	private String feedback;
	public RatingEntity() {
		super();
	}
	public RatingEntity(Long ratingId, Float star, Long ratedDriverDetailsId, String feedback) {
		super();
		this.ratingId = ratingId;
		this.star = star;
		this.ratedDriverDetailsId = ratedDriverDetailsId;
		this.feedback = feedback;
	}
	public Long getRatingId() {
		return ratingId;
	}
	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}
	public Float getStar() {
		return star;
	}
	public void setStar(Float star) {
		this.star = star;
	}
	public Long getRatedDriverDetailsId() {
		return ratedDriverDetailsId;
	}
	public void setRatedDriverDetailsId(Long ratedDriverDetailsId) {
		this.ratedDriverDetailsId = ratedDriverDetailsId;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
	public Long getRatedByUserId() {
		return ratedByUserId;
	}
	public void setRatedByUserId(Long ratedByUserId) {
		this.ratedByUserId = ratedByUserId;
	}
	@Override
	public String toString() {
		return "RatingEntity [ratingId=" + ratingId + ", star=" + star + ", ratedDriverDetailsId="
				+ ratedDriverDetailsId + ", feedback=" + feedback + "]";
	}
	
	


}
