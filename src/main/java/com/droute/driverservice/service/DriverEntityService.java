package com.droute.driverservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.droute.driverservice.Feign.client.UserServiceClient;
import com.droute.driverservice.dto.UserEntity;
import com.droute.driverservice.dto.request.LoginUserRequestDto;
import com.droute.driverservice.dto.request.RegisterUserRequestDto;
import com.droute.driverservice.dto.request.RequestDriverProfileDetailsDto;
import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.entity.DriverEntity;
import com.droute.driverservice.entity.Role;
import com.droute.driverservice.exception.BadRequestException;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.exception.UserServiceException;
import com.droute.driverservice.repository.DriverEntityRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DriverEntityService {

	// @Autowired
	// private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(DriverEntityService.class);

	@Autowired
	private DriverEntityRepository driverEntityRepository;
	@Autowired
	private UserServiceClient userServiceClient;

	// private final String commonUserEndPoint =
	// "http://localhost:8080/droute-user-service/api/user";

	public DriverEntity findDriverByDriverId(Long driverId) {
		return driverEntityRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Driver not found with given id = " + driverId));
	}

	public CommonResponseDto<UserEntity> loginDriver(LoginUserRequestDto loginDetails)
			throws BadRequestException, UserServiceException {
		System.out.println("login driver called.");
		return userServiceClient.loginUser(loginDetails);
	}

	public CommonResponseDto<UserEntity> registerDriver(RegisterUserRequestDto driverDetails) {

		logger.info("Driver sign-up service called");
		return userServiceClient.registerUser(driverDetails);

	}

	public DriverEntity getDriverByUserId(Long userId) {

		return driverEntityRepository.findByDriverDetailsId(userId);
	}

	public boolean checkDriverExistByUserId(Long userId) {

		return driverEntityRepository.existsByDriverDetailsId(userId);
	}

	public DriverEntity completeDriverProfile(RequestDriverProfileDetailsDto driverProfileDetails)
			throws EntityAlreadyExistsException {

		var existingUser = userServiceClient.getUserById(driverProfileDetails.getUserId()).getEntity();
		if (!existingUser.getRoles().contains(Role.DRIVER)) {
			throw new BadRequestException("User id is not associated with driver account!");
		}
		// Case when driver already exists i.e no need to enter the details again
		if (checkDriverExistByUserId(driverProfileDetails.getUserId())) {
			throw new EntityAlreadyExistsException("Driver Details already exists!");
		}

		// Case when driver's profile does not exists
		var driver = new DriverEntity();
		driver.setDriverDetailsId(driverProfileDetails.getUserId());
		driver.setVehicleNumber(driverProfileDetails.getVehicleNumber());
		driver.setDrivingLicenceNo(driverProfileDetails.getDrivingLicenceNo());
		driver.setVehicleName(driverProfileDetails.getVehicleName());
		driver.setVehicleType(driverProfileDetails.getVehicleType());
		driver.setRcNumber(driverProfileDetails.getRcNumber());

		driver.setAccountHolderName(driverProfileDetails.getAccountHolderName());
		driver.setDriverBankName(driverProfileDetails.getDriverBankName());
		driver.setDriverAccountNo(driverProfileDetails.getDriverAccountNo());
		driver.setDriverIfsc(driverProfileDetails.getDriverIfsc());
		driver.setDriverUpiId(driverProfileDetails.getDriverUpiId());
		driver.setAadharNumber(driverProfileDetails.getAadharNumber());
		logger.info("" + driver);

		return driverEntityRepository.save(driver);

	}

	/*
	 * // case when user with driver role not found
	 * if (existingUser.getStatusCode() != HttpStatus.OK ||
	 * !existingUser.getBody().getEntity().getRoles().contains(Role.valueOf("DRIVER"
	 * ))) {
	 * throw new EntityNotFoundException("Driver Not exists with given Id");
	 * }
	 * 
	 * // Case when driver already exists i.e no need to enter the details again
	 * if (getDriverByUserId(driverProfileDetails.getUserId()) != null)
	 * throw new EntityAlreadyExistsException("Driver already exists!");
	 * 
	 * // Case when driver's profile does not exists
	 * var driver = new DriverEntity();
	 * driver.setDriverDetailsId(driverProfileDetails.getUserId());
	 * driver.setVehicleNumber(driverProfileDetails.getVehicleNumber());
	 * driver.setDrivingLicenceNo(driverProfileDetails.getDrivingLicenceNo());
	 * driver.setVehicleName(driverProfileDetails.getVehicleName());
	 * driver.setVehicleType(driverProfileDetails.getVehicleType());
	 * driver.setRcNumber(driverProfileDetails.getRcNumber());
	 * 
	 * driver.setAccountHolderName(driverProfileDetails.getAccountHolderName());
	 * driver.setDriverBankName(driverProfileDetails.getDriverBankName());
	 * driver.setDriverAccountNo(driverProfileDetails.getDriverAccountNo());
	 * driver.setDriverIfsc(driverProfileDetails.getDriverIfsc());
	 * driver.setDriverUpiId(driverProfileDetails.getDriverUpiId());
	 * driver.setAadharNumber(driverProfileDetails.getAadharNumber());
	 * logger.info("" + driver);
	 * 
	 * return driverEntityRepository.save(driver);
	 */
}
