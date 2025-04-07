package com.droute.driverservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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
import com.droute.driverservice.exception.ErrorMessage;
import com.droute.driverservice.exception.UserServiceException;
import com.droute.driverservice.repository.DriverEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DriverEntityService {

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(DriverEntityService.class);

	@Autowired
	private DriverEntityRepository driverEntityRepository;
	@Autowired
	private UserServiceClient userServiceClient;

	private final String commonUserEndPoint = "http://localhost:8080/droute-user-service/api/user";

	public DriverEntity findDriverByDriverId(Long driverId) {
		return driverEntityRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Driver not found with given id = " + driverId));
	}
	// private CommonResponseDto<UserEntity> handleErrorResponse(String
	// responseBody) {
	// try {
	// ObjectMapper objectMapper = new ObjectMapper();

	// // Try to deserialize into CommonResponseDto first
	// CommonResponseDto<UserEntity> commonResponse = objectMapper.readValue(
	// responseBody, new TypeReference<CommonResponseDto<UserEntity>>() {}
	// );

	// // If successful, return the CommonResponseDto
	// return commonResponse;

	// } catch (JsonProcessingException e) {
	// // If deserialization to CommonResponseDto fails, try ErrorMessage format
	// try {
	// ErrorMessage errorMessage = new ObjectMapper().readValue(responseBody,
	// ErrorMessage.class);

	// // Create a CommonResponseDto for the error message
	// return new CommonResponseDto<>(errorMessage.getMessageDetails(), null);
	// } catch (JsonProcessingException ex) {
	// // If both deserialization attempts fail, return a generic error message
	// return new CommonResponseDto<>("Error parsing response", null);
	// }
	// }
	// }

	public CommonResponseDto<UserEntity> loginDriver(LoginUserRequestDto loginDetails)
			throws BadRequestException, UserServiceException {
				System.out.println("login driver called.");
		return userServiceClient.loginUser(loginDetails);
	}

	/*
	 * public ResponseEntity<CommonResponseDto<UserEntity>> loginDriver(String
	 * emailOrPhone, String password) {
	 * String url = commonUserEndPoint +
	 * "/auth/login?emailOrPhone={emailOrPhone}&password={password}";
	 * 
	 * try {
	 * // Making the GET request
	 * ResponseEntity<CommonResponseDto<UserEntity>> response =
	 * restTemplate.exchange(url, HttpMethod.GET, null, // No
	 * // request
	 * // body
	 * new ParameterizedTypeReference<CommonResponseDto<UserEntity>>() {
	 * }, emailOrPhone, password);
	 * 
	 * // Return the ResponseEntity directly
	 * return response;
	 * 
	 * } catch (HttpClientErrorException | HttpServerErrorException ex) {
	 * // Handle error responses (e.g., 400 or 500 HTTP errors)
	 * String responseBody = ex.getResponseBodyAsString();
	 * CommonResponseDto<UserEntity> errorResponse =
	 * handleErrorResponse(responseBody);
	 * return ResponseEntity.status(ex.getStatusCode()).body(errorResponse); //
	 * Return ResponseEntity with error
	 * } catch (Exception ex) {
	 * // General exception handling
	 * CommonResponseDto<UserEntity> errorResponse = new
	 * CommonResponseDto<>("Internal server error", null);
	 * return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	 * // Return ResponseEntity
	 * // with error
	 * }
	 * }
	 * public ResponseEntity<CommonResponseDto<UserEntity>> getUserById(Long userId)
	 * {
	 * String url = commonUserEndPoint + "/"+userId;
	 * 
	 * try {
	 * // Making the GET request
	 * ResponseEntity<CommonResponseDto<UserEntity>> response =
	 * restTemplate.exchange(url, HttpMethod.GET, null, // No
	 * // request
	 * // body
	 * new ParameterizedTypeReference<CommonResponseDto<UserEntity>>() {
	 * });
	 * 
	 * // Return the ResponseEntity directly
	 * return response;
	 * 
	 * } catch (HttpClientErrorException | HttpServerErrorException ex) {
	 * // Handle error responses (e.g., 400 or 500 HTTP errors)
	 * String responseBody = ex.getResponseBodyAsString();
	 * CommonResponseDto<UserEntity> errorResponse =
	 * handleErrorResponse(responseBody);
	 * return ResponseEntity.status(ex.getStatusCode()).body(errorResponse); //
	 * Return ResponseEntity with error
	 * } catch (Exception ex) {
	 * // General exception handling
	 * CommonResponseDto<UserEntity> errorResponse = new
	 * CommonResponseDto<>("Internal server error", null);
	 * return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	 * // Return ResponseEntity
	 * // with error
	 * }
	 * }
	 * 
	 */

	public CommonResponseDto<UserEntity> registerDriver(RegisterUserRequestDto driverDetails) {

		logger.info("Driver sign-up service called");
		return userServiceClient.registerUser(driverDetails);

	}

	public DriverEntity getDriverByUserId(Long userId) {

		return driverEntityRepository.findByDriverDetailsId(userId);
	}
	/*
	 * public DriverEntity completeDriverProfile(RequestDriverProfileDetailsDto
	 * driverProfileDetails) throws EntityAlreadyExistsException {
	 * 
	 * var existingUser = getUserById(driverProfileDetails.getUserId());
	 * 
	 * // case when user with driver role not found
	 * if(existingUser.getStatusCode() != HttpStatus.OK ||
	 * !existingUser.getBody().getEntity().getRoles().contains(Role.valueOf("DRIVER"
	 * ))) {
	 * throw new EntityNotFoundException("Driver Not exists with given Id");
	 * }
	 * // Case when driver already exists i.e no need to enter the details again
	 * if(getDriverByUserId(driverProfileDetails.getUserId()) != null) throw new
	 * EntityAlreadyExistsException("Driver already exists!");
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
	 * 
	 * driver.setAccountHolderName(driverProfileDetails.getAccountHolderName());
	 * driver.setDriverBankName(driverProfileDetails.getDriverBankName());
	 * driver.setDriverAccountNo(driverProfileDetails.getDriverAccountNo());
	 * driver.setDriverIfsc(driverProfileDetails.getDriverIfsc());
	 * driver.setDriverUpiId(driverProfileDetails.getDriverUpiId());
	 * driver.setAadharNumber(driverProfileDetails.getAadharNumber());
	 * logger.info(""+driver);
	 * 
	 * return driverEntityRepository.save(driver);
	 * 
	 * }
	 */

}
