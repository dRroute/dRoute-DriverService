package com.droute.driverservice.entity;

import java.time.LocalDateTime;

import com.droute.driverservice.enums.JourneyStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JourneyDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journeyId;
    
    private Long driverId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journey_source_id", referencedColumnName = "locationId")
//    @JsonIgnore
    private LocationDetailsEntity journeySource;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journey_destination_id", referencedColumnName = "locationId")
//    @JsonIgnore
    private LocationDetailsEntity journeyDestination;
    
    
    private Double availableLength;
    private Double availableWidth;
    private Double availableHeight;
    private String availableSpaceMeasurementType;
    
    @Enumerated(EnumType.STRING)
    private JourneyStatus  status;
    
    private LocalDateTime expectedDepartureDateTime;
    private LocalDateTime expectedArrivalDateTime;
    
    

    public JourneyDetailEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	// Constructor
    public JourneyDetailEntity(Long driverId, LocationDetailsEntity journeySource, LocationDetailsEntity journeyDestination) {
        this.driverId = driverId;
        this.journeySource = journeySource;
        this.journeyDestination = journeyDestination;
    }


	public JourneyDetailEntity(Long journeyId, Long driverId, LocationDetailsEntity journeySource,
			LocationDetailsEntity journeyDestination, Double availableLength, Double availableWidth,
			Double availableHeight, String availableSpaceMeasurementType, JourneyStatus status,
			LocalDateTime expectedDepartureDateTime, LocalDateTime expectedArrivalDateTime) {
		super();
		this.journeyId = journeyId;
		this.driverId = driverId;
		this.journeySource = journeySource;
		this.journeyDestination = journeyDestination;
		this.availableLength = availableLength;
		this.availableWidth = availableWidth;
		this.availableHeight = availableHeight;
		this.availableSpaceMeasurementType = availableSpaceMeasurementType;
		this.status = status;
		this.expectedDepartureDateTime = expectedDepartureDateTime;
		this.expectedArrivalDateTime = expectedArrivalDateTime;
	}



	// Getter and Setter methods
    public Long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public LocationDetailsEntity getJourneySource() {
        return journeySource;
    }

    public void setJourneySource(LocationDetailsEntity journeySource) {
        this.journeySource = journeySource;
    }

    public LocationDetailsEntity getJourneyDestination() {
        return journeyDestination;
    }

    public void setJourneyDestination(LocationDetailsEntity journeyDestination) {
        this.journeyDestination = journeyDestination;
    }

    public Double getAvailableLength() {
		return availableLength;
	}

	public void setAvailableLength(Double availableLength) {
		this.availableLength = availableLength;
	}

	public Double getAvailableWidth() {
		return availableWidth;
	}

	public void setAvailableWidth(Double availableWidth) {
		this.availableWidth = availableWidth;
	}

	public Double getAvailableHeight() {
		return availableHeight;
	}

	public void setAvailableHeight(Double availableHeight) {
		this.availableHeight = availableHeight;
	}

	public String getAvailableSpaceMeasurementType() {
		return availableSpaceMeasurementType;
	}

	public void setAvailableSpaceMeasurementType(String availableSpaceMeasurementType) {
		this.availableSpaceMeasurementType = availableSpaceMeasurementType;
	}

	public JourneyStatus getStatus() {
		return status;
	}

	public void setStatus(JourneyStatus status) {
		this.status = status;
	}

	public LocalDateTime getExpectedDepartureDateTime() {
		return expectedDepartureDateTime;
	}

	public void setExpectedDepartureDateTime(LocalDateTime expectedDepartureDateTime) {
		this.expectedDepartureDateTime = expectedDepartureDateTime;
	}

	public LocalDateTime getExpectedArrivalDateTime() {
		return expectedArrivalDateTime;
	}

	public void setExpectedArrivalDateTime(LocalDateTime expectedArrivalDateTime) {
		this.expectedArrivalDateTime = expectedArrivalDateTime;
	}

	@Override
	public String toString() {
		return "JourneyDetailEntity [journeyId=" + journeyId + ", driverId=" + driverId + ", journeySource="
				+ journeySource + ", journeyDestination=" + journeyDestination + ", availableLength=" + availableLength
				+ ", availableWidth=" + availableWidth + ", availableHeight=" + availableHeight
				+ ", availableSpaceMeasurementType=" + availableSpaceMeasurementType + ", status=" + status
				+ ", expectedDepartureDateTime=" + expectedDepartureDateTime + ", expectedArrivalDateTime="
				+ expectedArrivalDateTime + "]";
	}


	
}

