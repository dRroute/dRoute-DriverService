package com.droute.driverservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.droute.driverservice.dto.CommonResponseDto;
import com.droute.driverservice.dto.RegisterUserRequestDto;
import com.droute.driverservice.entity.UserEntity;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.service.DriverEntityService;

@RestController
@RequestMapping("/api/driver")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private DriverEntityService driveEntityService;

	@GetMapping("/login")
	public ResponseEntity<CommonResponseDto<UserEntity>> loginDriver(@RequestParam String emailOrPhone,
			@RequestParam String password) {
		var response = driveEntityService.loginDriver(emailOrPhone, password);
		var driver = response.getBody().getEntity();
		if(driver !=null && driver.getRole().equalsIgnoreCase("DRIVER")) {
			return response;
		}else if(driver !=null && !driver.getRole().equalsIgnoreCase("DRIVER")) {
			response.getBody().setEntity(null);
			response.getBody().setMessage("Given credential is not associated with Driver Account");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());
		}
		logger.info("driver = "+response.getBody().getEntity());
		return response; // Return the ResponseEntity directly
	}
	
	@PostMapping("/signup")
	public ResponseEntity<CommonResponseDto<UserEntity>> createDriverAccount(@RequestBody RegisterUserRequestDto driverDetails) throws EntityAlreadyExistsException {
		if(driverDetails.getRole().equalsIgnoreCase("driver")) {
		var response  = driveEntityService.registerDriver(driverDetails);
		return response;
		
		
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponseDto<>("Invalid role given",null));
		

	}

}
