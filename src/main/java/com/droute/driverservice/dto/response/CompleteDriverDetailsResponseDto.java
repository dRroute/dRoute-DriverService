package com.droute.driverservice.dto.response;



import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CompleteDriverDetailsResponseDto {
   
    private Long userId;
    private Long driverId;
    
    private String fullName;
    private String email;
    private String contactNo;
    private String role;

	private String vehicleNumber;
	private String drivingLicenceNo;
	private String vehicleName;
	private String vehicleType;

	private String accountHolderName;
	private String driverBankName;
	private String driverAccountNo;
	private String driverIfsc;
	private String driverUpiId;
	private String aadharNumber;
	private String profileStatus; // Enum for ACTIVE, INACTIVE, BLOCKED, etc.

	private Set<DocumentResponseDto> documents; 
}
