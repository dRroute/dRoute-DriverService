package com.droute.driverservice.dto.response;

public class ResponseDriverProfileDetailsDto {

	
	private String vehicleNumber;
	private String drivingLicenceNo;
	private String vehicleName;
	private String vehicleType;

	private String accountHolderName;
	private String driverAccountNo;
	private String driverIfsc;
	private String driverUpiId;
	
	public ResponseDriverProfileDetailsDto() {
		super();
	}
	public ResponseDriverProfileDetailsDto(String vehicleNumber, String drivingLicenceNo, String vehicleName,
			String vehicleType, String accountHolderName, String driverAccountNo, String driverIfsc,
			String driverUpiId) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.drivingLicenceNo = drivingLicenceNo;
		this.vehicleName = vehicleName;
		this.vehicleType = vehicleType;
		this.accountHolderName = accountHolderName;
		this.driverAccountNo = driverAccountNo;
		this.driverIfsc = driverIfsc;
		this.driverUpiId = driverUpiId;
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
	
	
	

}
