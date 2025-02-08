package com.droute.driverservice.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="driver_entity")
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    
    private Long driverDetailsId; // Reference to User in another microservice
    
    private String vehicleNumber;
    private String drivingLicenceNo;
    private String vehicleName;
    private String vehicleType;
    
    @OneToMany(mappedBy = "ratingId", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;
    
    private String accountHolderName;
    private String driverAccountNo;
    private String  driverIfsc;
    private String driverUpiId;
	public DriverEntity(Long driverId, Long driverDetailsId, String vehicleNumber, String drivingLicenceNo,
			String vehicleName, String vehicleType, List<RatingEntity> ratings, String accountHolderName,
			String driverAccountNo, String driverIfsc, String driverUpiId) {
		super();
		this.driverId = driverId;
		this.driverDetailsId = driverDetailsId;
		this.vehicleNumber = vehicleNumber;
		this.drivingLicenceNo = drivingLicenceNo;
		this.vehicleName = vehicleName;
		this.vehicleType = vehicleType;
		this.ratings = ratings;
		this.accountHolderName = accountHolderName;
		this.driverAccountNo = driverAccountNo;
		this.driverIfsc = driverIfsc;
		this.driverUpiId = driverUpiId;
	}
	public DriverEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public Long getDriverDetailsId() {
		return driverDetailsId;
	}
	public void setDriverDetailsId(Long driverDetailsId) {
		this.driverDetailsId = driverDetailsId;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getDrivingLicenceNo() {
		return drivingLicenceNo;
	}
	public void setDrivingLicenceNo(String drivingLicenceNo) {
		this.drivingLicenceNo = drivingLicenceNo;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public List<RatingEntity> getRatings() {
		return ratings;
	}
	public void setRatings(List<RatingEntity> ratings) {
		this.ratings = ratings;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public String getDriverAccountNo() {
		return driverAccountNo;
	}
	public void setDriverAccountNo(String driverAccountNo) {
		this.driverAccountNo = driverAccountNo;
	}
	public String getDriverIfsc() {
		return driverIfsc;
	}
	public void setDriverIfsc(String driverIfsc) {
		this.driverIfsc = driverIfsc;
	}
	public String getDriverUpiId() {
		return driverUpiId;
	}
	public void setDriverUpiId(String driverUpiId) {
		this.driverUpiId = driverUpiId;
	}
	@Override
	public String toString() {
		return "DriverEntity [driverId=" + driverId + ", driverDetailsId=" + driverDetailsId + ", vehicleNumber="
				+ vehicleNumber + ", drivingLicenceNo=" + drivingLicenceNo + ", vehicleName=" + vehicleName
				+ ", vehicleType=" + vehicleType + ", ratings=" + ratings + ", accountHolderName=" + accountHolderName
				+ ", driverAccountNo=" + driverAccountNo + ", driverIfsc=" + driverIfsc + ", driverUpiId=" + driverUpiId
				+ "]";
	}
    
    
    
}
