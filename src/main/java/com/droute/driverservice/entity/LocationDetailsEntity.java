package com.droute.driverservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LocationDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String longitude;
    private String latitude;
    
    private String address;
    
    private String city;
    private String pinode;
    private String country;

////    @OneToMany(mappedBy = "journeySource")
//    private List<JourneyDetailEntity> sourceJourneys;
//    
////    @OneToMany(mappedBy = "journeyDestination")
//    private List<JourneyDetailEntity> destinationJourneys;

    // Constructor
    
    
    public LocationDetailsEntity(String longitude, String latitude, String address, String city, String pinode, String country) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.city = city;
        this.pinode = pinode;
        this.country = country;
    }

    public LocationDetailsEntity() {
		super();
	}

	// Getter and Setter methods
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinode() {
        return pinode;
    }

    public void setPinode(String pinode) {
        this.pinode = pinode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public List<JourneyDetailEntity> getSourceJourneys() {
//        return sourceJourneys;
//    }
//
//    public void setSourceJourneys(List<JourneyDetailEntity> sourceJourneys) {
//        this.sourceJourneys = sourceJourneys;
//    }
//
//    public List<JourneyDetailEntity> getDestinationJourneys() {
//        return destinationJourneys;
//    }
//
//    public void setDestinationJourneys(List<JourneyDetailEntity> destinationJourneys) {
//        this.destinationJourneys = destinationJourneys;
//    }

    @Override
    public String toString() {
        return "LocationDetailsEntity{" +
                "locationId=" + locationId +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", pinode='" + pinode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

