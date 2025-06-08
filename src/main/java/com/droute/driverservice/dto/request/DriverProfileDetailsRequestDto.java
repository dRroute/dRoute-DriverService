package com.droute.driverservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DriverProfileDetailsRequestDto {

	@NotNull(message = "User ID cannot be null")
	private Long driverId;

	@NotNull(message = "Vehicle Number cannot be null")
	private String vehicleNumber;

	@NotNull(message = "Driving Licence Number cannot be null")
	private String drivingLicenceNo;

	@NotNull(message = "Vehicle Name cannot be null")
	private String vehicleName;

	@NotNull(message = "Vehicle Type cannot be null")
	private String vehicleType;

	@NotNull(message = "Account Holder Name cannot be null")
	private String accountHolderName;

	@NotNull(message = "Bank Name cannot be null")
	private String driverBankName;

	@NotNull(message = "Account Number cannot be null")
	private String driverAccountNo;

	@NotNull(message = "IFSC Code cannot be null")
	private String driverIfsc;

	@NotNull(message = "UPI ID cannot be null")
	private String driverUpiId;

	@NotNull(message = "Aadhar Number cannot be null")
	private String aadharNumber;
	
	

}
