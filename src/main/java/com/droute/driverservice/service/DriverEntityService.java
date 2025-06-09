package com.droute.driverservice.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.dto.UserEntity;
import com.droute.driverservice.dto.request.DriverProfileDetailsRequestDto;
import com.droute.driverservice.dto.request.LoginUserRequestDto;
import com.droute.driverservice.dto.request.RegisterUserRequestDto;
import com.droute.driverservice.dto.request.ResetPasswordRequestDTO;
import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.CompleteDriverDetailsResponseDto;
import com.droute.driverservice.dto.response.DocumentResponseDto;
import com.droute.driverservice.entity.DriverEntity;
import com.droute.driverservice.entity.Role;
import com.droute.driverservice.enums.ProfileStatus;
import com.droute.driverservice.exception.BadRequestException;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.exception.UserServiceException;
import com.droute.driverservice.feign.client.UserServiceClient;
import com.droute.driverservice.repository.DriverEntityRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DriverEntityService {

	// @Autowired
	// private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(DriverEntityService.class);

	@Autowired
	private DriverEntityRepository driverRepository;

	@Autowired
	private UserServiceClient userServiceClient;

	public Float getDriverAvgRating(Long driverId) {
		logger.info("getDriverAvgRating called for driverId: {}", driverId);
		// Fetch the driver entity
		DriverEntity driver = findDriverByDriverId(driverId);

		var ratings = driver.getRatings();
		if (ratings == null || ratings.isEmpty()) {
			logger.warn("No ratings found for driverId: {}", driverId);
			return 0.0f; // Return 0 if no ratings exist
		}
		// Calculate the average rating
		Double avgRating = ratings.stream()
				.mapToDouble(rating -> rating.getStar())
				.average()
				.orElse(0.0); // Default to 0.0 if no ratings are present

		logger.info("Average rating for driverId {} is {}", driverId, avgRating);
		return avgRating.floatValue();
	}

	public DriverEntity findDriverByDriverId(Long driverId) {
		return driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Driver not found with given id = " + driverId));
	}

	public CompleteDriverDetailsResponseDto loginDriver(LoginUserRequestDto loginDetails)
			throws UserServiceException {
		logger.info("login driver called.");

		//Call user service to get and verify the credentials
		var user = userServiceClient.loginUser(loginDetails).getData();

		logger.info("User logged in successfully: {}", user);

		// Check if the user is a driver
		if (user != null && user.getRoles().contains(Role.DRIVER)) {
			// Fetch the driver entity using the userId
			DriverEntity driver = driverRepository.findByDriverDetailsId(user.getUserId());
			if (driver == null) {
				throw new EntityNotFoundException("Driver not found for userId: " + user.getUserId());
			}
			logger.info("Driver found: {}", driver);

			return CompleteDriverDetailsResponseDto.builder()
					.userId(user.getUserId())
					.fullName(user.getFullName())
					.email(user.getEmail())
					.contactNo(user.getContactNo())
					.role(user.getRoles().stream()
							.map((Role r) -> r.toString())
							.collect(Collectors.joining(", ")))
					.driverId(driver.getDriverId())
					.profileStatus(user.getStatus() == null ? null : user.getStatus().toString())
					.vehicleName(driver.getVehicleName())
					.vehicleNumber(driver.getVehicleNumber())
					.drivingLicenceNo(driver.getDrivingLicenceNo())

					.accountHolderName(driver.getAccountHolderName())
					.driverBankName(driver.getDriverBankName())
					.driverAccountNo(driver.getDriverAccountNo())
					.driverIfsc(driver.getDriverIfsc())
					.driverUpiId(driver.getDriverUpiId())
					.aadharNumber(driver.getAadharNumber())
					// Add other fields as necessary
					.build();
		} else {
			throw new BadRequestException("Given credential is not associated with Driver Account.");
		}

	}

	public CompleteDriverDetailsResponseDto registerDriver(RegisterUserRequestDto driverDetails) {

		logger.info("Driver sign-up service called");
		var user = userServiceClient.registerUser(driverDetails).getData();

		logger.info("User registered successfully: {}", user);
		// Create a new driver entity
		var driver = DriverEntity.builder()
				.driverDetailsId(user.getUserId())
				.build();
		 driver = driverRepository.save(driver);

		logger.info("Driver entity created: {}", driver);

		var driverdetails = CompleteDriverDetailsResponseDto.builder()
				.userId(user.getUserId())
				.fullName(user.getFullName())
				.email(user.getEmail())
				.contactNo(user.getContactNo())
				.role(user.getRoles().stream()
						.map(Enum::name)
						.collect(Collectors.joining(", ")))
				.driverId(driver.getDriverId())
				.profileStatus(user.getStatus() == null ? null : user.getStatus().toString())
				.build();
		return driverdetails;
	}

	public DriverEntity getDriverByUserId(Long userId) {

		return driverRepository.findByDriverDetailsId(userId);
	}

	public DriverEntity updateDriverProfileStatus(Long driverId, String status) {
		// Fetch the driver entity
		DriverEntity driverEntity = findDriverByDriverId(driverId);

		// Update the profile status
		var user = UserEntity.builder().status(ProfileStatus.valueOf(status.toUpperCase())).build();
		try {
			user = userServiceClient.updateUserDetails(user, driverEntity.getDriverDetailsId()).getData();
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("Invalid profile status: " + status);
		}

		return driverRepository.save(driverEntity);
	}

	public boolean checkDriverExistByUserId(Long userId) {

		return driverRepository.existsByDriverDetailsId(userId);
	}

	public boolean checkDriverExistByDriverId(Long driverId) {

		return driverRepository.existsById(driverId);
	}

	public CompleteDriverDetailsResponseDto completeDriverProfile(DriverProfileDetailsRequestDto driverProfileDetails)
			throws EntityAlreadyExistsException {

		var driver = findDriverByDriverId(driverProfileDetails.getDriverId());
		driver.setVehicleNumber(driverProfileDetails.getVehicleNumber());
		driver.setDrivingLicenceNo(driverProfileDetails.getDrivingLicenceNo());
		driver.setVehicleName(driverProfileDetails.getVehicleName());
		driver.setVehicleType(driverProfileDetails.getVehicleType());

		driver.setAccountHolderName(driverProfileDetails.getAccountHolderName());
		driver.setDriverBankName(driverProfileDetails.getDriverBankName());
		driver.setDriverAccountNo(driverProfileDetails.getDriverAccountNo());
		driver.setDriverIfsc(driverProfileDetails.getDriverIfsc());
		driver.setDriverUpiId(driverProfileDetails.getDriverUpiId());
		driver.setAadharNumber(driverProfileDetails.getAadharNumber());

		driver = driverRepository.save(driver);

		logger.info("Driver profile completed successfully: {}", driver);
		var user = UserEntity.builder().status(ProfileStatus.PENDING_VERIFICATION).build();
		user = userServiceClient.updateUserDetails(user, driver.getDriverDetailsId()).getData();
		return getFullDriverDetails(driver, user);

	}

	public CompleteDriverDetailsResponseDto getFullDriverDetails(DriverEntity driver, UserEntity user) {

		logger.info("getFullDriverDetails called for driverId: {}", driver.getDriverId());

		// Map to response DTO
		CompleteDriverDetailsResponseDto completeDetails = new CompleteDriverDetailsResponseDto();
		completeDetails.setUserId(user.getUserId());
		completeDetails.setDriverId(driver.getDriverId());
		completeDetails.setFullName(user.getFullName());
		completeDetails.setEmail(user.getEmail());
		completeDetails.setContactNo(user.getContactNo());

		String role = user.getRoles()
				.stream()
				.map(Enum::name)
				.collect(Collectors.joining(", "));

		completeDetails.setRole(role);

		completeDetails.setVehicleNumber(driver.getVehicleNumber());
		completeDetails.setDrivingLicenceNo(driver.getDrivingLicenceNo());
		completeDetails.setVehicleName(driver.getVehicleName());
		completeDetails.setVehicleType(driver.getVehicleType());

		completeDetails.setAccountHolderName(driver.getAccountHolderName());
		completeDetails.setDriverBankName(driver.getDriverBankName());
		completeDetails.setDriverAccountNo(driver.getDriverAccountNo());
		completeDetails.setDriverIfsc(driver.getDriverIfsc());
		completeDetails.setDriverUpiId(driver.getDriverUpiId());

		completeDetails.setAadharNumber(driver.getAadharNumber());
		completeDetails.setProfileStatus(user.getStatus().toString());

		Set<DocumentResponseDto> documentDtos = driver.getDocuments()
				.stream()
				.map(doc -> DocumentResponseDto.builder()
						.documentId(doc.getDocumentId())
						.documentName(doc.getDocumentName())
						.documentType(doc.getDocumentType())
						.documentUrl(doc.getDocumentUrl())
						.build())
				.collect(Collectors.toSet());

		completeDetails.setDocuments(documentDtos);
		// completeDetails.setDocuments(driver.getDocuments());

		logger.info("Complete details = {}", completeDetails);
		return completeDetails;
	}

	public CompleteDriverDetailsResponseDto getDriverById(Long driverId) {
		System.out.println("get driver by id called.");
		// 1. Get Driver data
		DriverEntity driver = driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Driver not found with id = " + driverId));
		logger.info("Driver profile completed successfully: {}", driver);
		// 2. Get User data
		
		var user = userServiceClient.getUserById(driver.getDriverDetailsId()).getData();

		// Convert Entity into DTO
		return getFullDriverDetails(driver, user);

	}

	public CommonResponseDto<String> resetDriverPassword(ResetPasswordRequestDTO requestDto) {
		var userResponse = userServiceClient.resetUserPassword(requestDto);
		if (userResponse.getStatusCode() != 200) {
			throw new EntityNotFoundException("User not found with email = " + requestDto.getEmail());
		}
		return userResponse;
	}

}
