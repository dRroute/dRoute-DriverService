package com.droute.driverservice.dto;

public class RequestDriverProfileDetailsDto {

	private Long userId;
	private String vehicleNumber;
	private String drivingLicenceNo;
	private String vehicleName;
	private String vehicleType;

	private String accountHolderName;
	private String driverAccountNo;
	private String driverIfsc;
	private String driverUpiId;
	
	public RequestDriverProfileDetailsDto() {
		super();
	}
	public RequestDriverProfileDetailsDto(Long userId, String vehicleNumber, String drivingLicenceNo,
			String vehicleName, String vehicleType, String accountHolderName, String driverAccountNo, String driverIfsc,
			String driverUpiId) {
		super();
		this.userId = userId;
		this.vehicleNumber = vehicleNumber;
		this.drivingLicenceNo = drivingLicenceNo;
		this.vehicleName = vehicleName;
		this.vehicleType = vehicleType;
		this.accountHolderName = accountHolderName;
		this.driverAccountNo = driverAccountNo;
		this.driverIfsc = driverIfsc;
		this.driverUpiId = driverUpiId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
		return "RequestDriverProfileDetailsDto [userId=" + userId + ", vehicleNumber=" + vehicleNumber
				+ ", drivingLicenceNo=" + drivingLicenceNo + ", vehicleName=" + vehicleName + ", vehicleType="
				+ vehicleType + ", accountHolderName=" + accountHolderName + ", driverAccountNo=" + driverAccountNo
				+ ", driverIfsc=" + driverIfsc + ", driverUpiId=" + driverUpiId + "]";
	}
	
	

}
