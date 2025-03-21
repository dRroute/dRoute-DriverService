package com.droute.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestDriverProfileDetailsDto {

	private Long userId;
	private String vehicleNumber;
	private String drivingLicenceNo;
	private String vehicleName;
	private String vehicleType;
	private String rcNumber;

	private String accountHolderName;
	private String driverBankName;
	private String driverAccountNo;
	private String driverIfsc;
	private String driverUpiId;
	private String aadharNumber;
	
	

}
