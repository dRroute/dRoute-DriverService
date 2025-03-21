package com.droute.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserRequestDto {
	
	private String fullName;
	private String email;
	private String password;
	private String contactNo;
	private String role;
	


}
