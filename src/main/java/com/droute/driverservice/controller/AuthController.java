package com.droute.driverservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.droute.driverservice.dto.UserEntity;
import com.droute.driverservice.dto.request.LoginUserRequestDto;
import com.droute.driverservice.dto.request.RegisterUserRequestDto;
import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.CompleteDriverDetailsResponseDto;
import com.droute.driverservice.dto.response.ResponseBuilder;
import com.droute.driverservice.entity.Role;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.service.DriverEntityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/driver")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private DriverEntityService driveEntityService;

	@PostMapping("/login")
	public ResponseEntity<CommonResponseDto<CompleteDriverDetailsResponseDto>> loginDriver(@RequestBody LoginUserRequestDto loginDetails) {
		if (!loginDetails.getRole().equalsIgnoreCase("driver")) {
			return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Role Must be Driver."); // Return the ResponseEntity directly

		}
		var response = driveEntityService.loginDriver(loginDetails);

		return ResponseBuilder.success(HttpStatus.OK, "Logged-In Successfully", response);

		/* var driver = response.getData();

		// This condition is added to check if the user is a driver or not
		if (driver != null && (driver.getRoles().contains(Role.DRIVER) || driver.getRoles().contains(Role.ADMIN))) {
			return ResponseBuilder.success(HttpStatus.OK, "Logged in successfully.", driver);

		}
		// If the user is not a driver then return the response with message
		else if (driver != null
				&& !(driver.getRoles().contains(Role.DRIVER) || driver.getRoles().contains(Role.ADMIN))) {
			

			return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Given credential is not associated with Driver Account.");
		}
		logger.info("driver = " + response.getData());
		// When the driver is not found
		return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Driver Account not found."); // Return the ResponseEntity directly
	
		*/}

	@PostMapping("/signup")
	public ResponseEntity<CommonResponseDto<CompleteDriverDetailsResponseDto>> createDriverAccount(
			@Valid @RequestBody RegisterUserRequestDto driverDetails) throws EntityAlreadyExistsException {
		// Check if the role is driver or not
		if (driverDetails.getRole().equalsIgnoreCase("driver")) {
		
			var response = driveEntityService.registerDriver(driverDetails);
			return ResponseBuilder.success(HttpStatus.CREATED, "Driver registration Successfull", response);

		}
		
		return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Invalid role given");
	}

}
