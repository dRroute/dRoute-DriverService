package com.droute.driverservice.service;

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


	public DriverEntity findDriverByDriverId(Long driverId) {
		return driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Driver not found with given id = " + driverId));
	}

	public CompleteDriverDetailsResponseDto loginDriver(LoginUserRequestDto loginDetails)
			throws  UserServiceException {
		logger.info("login driver called.");
		var user =  userServiceClient.loginUser(loginDetails);
		if (user.getStatusCode() != 200) {
			throw new UserServiceException("User login failed: " + user.getMessage());
		}
		logger.info("User logged in successfully: {}", user.getData());

		// Check if the user is a driver
		if (user.getData() != null && user.getData().getRoles().contains(Role.DRIVER)) {
			// Fetch the driver entity using the userId
			DriverEntity driver = driverRepository.findByDriverDetailsId(user.getData().getUserId());
			if (driver == null) {
				throw new EntityNotFoundException("Driver not found for userId: " + user.getData().getUserId());
			}
			logger.info("Driver found: {}", driver);

			return CompleteDriverDetailsResponseDto.builder()
				.userId(user.getData().getUserId())
				.fullName(user.getData().getFullName())
				.email(user.getData().getEmail())
				.contactNo(user.getData().getContactNo())
				.role(user.getData().getRoles().stream()
						.map((Role r) -> r.toString())
						.collect(Collectors.joining(", ")))
				.driverId(driver.getDriverId())
				.profileStatus(driver.getProfileStatus().toString())
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
		var user =  userServiceClient.registerUser(driverDetails);
		if (user.getStatusCode() != 200) {
			throw new UserServiceException("User registration failed: " + user.getMessage());
		}
		logger.info("User registered successfully: {}", user.getData());
		// Create a new driver entity
		DriverEntity driver = driverRepository.save( DriverEntity.builder()
				.profileStatus(ProfileStatus.PENDING_COMPLETION)
				.driverDetailsId(user.getData().getUserId())
				.build());

				logger.info("Driver entity created: {}", driver);
	
		var driverdetails = CompleteDriverDetailsResponseDto.builder()
				.userId(user.getData().getUserId())
				.fullName(user.getData().getFullName())
				.email(user.getData().getEmail())
				.contactNo(user.getData().getContactNo())
				.role(user.getData().getRoles().stream()
						.map(Enum::name)
						.collect(Collectors.joining(", ")))
				.driverId(driver.getDriverId())
				.profileStatus(driver.getProfileStatus().toString())
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
		try {
			driverEntity.setProfileStatus(ProfileStatus.valueOf(status.toUpperCase()));
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

		driver.setDriverDetailsId(driverProfileDetails.getDriverId());
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

		driver.setProfileStatus(ProfileStatus.PENDING_VERIFICATION);
	
		driver =  driverRepository.save(driver);

		logger.info("Driver profile completed successfully: {}", driver);
		return getFullDriverDetails(driver);

	}

	public CompleteDriverDetailsResponseDto getFullDriverDetails(DriverEntity driver){
		logger.info("getFullDriverDetails called for driverId: {}", driver.getDriverId());
		
		// 1. Get user data
		var user = userServiceClient.getUserById(driver.getDriverDetailsId());
		if (user.getData() == null) {
			throw new EntityNotFoundException("User not found with id = " + driver.getDriverDetailsId());
		}
		logger.info("User data retrieved: {}", user.getData());

		// 2. Map to response DTO
		CompleteDriverDetailsResponseDto completeDetails = new CompleteDriverDetailsResponseDto();
		completeDetails.setUserId(user.getData().getUserId());
		completeDetails.setDriverId(driver.getDriverId());
		completeDetails.setFullName(user.getData().getFullName());
		completeDetails.setEmail(user.getData().getEmail());
		completeDetails.setContactNo(user.getData().getContactNo());

		String role = user.getData().getRoles()
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
		completeDetails.setProfileStatus(driver.getProfileStatus().toString());

		return completeDetails;
	}
	

	public CompleteDriverDetailsResponseDto getDriverById(Long driverId) {
		System.out.println("get driver by id called.");
		// 1. Get user data
		DriverEntity driverEntity = driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Driver not found with id = " + driverId));
		
		return getFullDriverDetails(driverEntity);
	}

	// public CompleteDriverDetailsResponseDto updateDriver(Long driverId,
	// 		CompleteDriverDetailsResponseDto updateRequest) {
	// 	// Fetch the driver entity
	// 	DriverEntity driverEntity = driverRepository.findById(driverId)
	// 			.orElseThrow(() -> new EntityNotFoundException("Driver not found with id = " + driverId));

	// 	// Fetch the user entity linked to the driver
	// 	var userResponse = userServiceClient.getUserById(driverEntity.getDriverDetailsId());
	// 	if (userResponse.getData() == null) {
	// 		throw new EntityNotFoundException("User not found with id = " + driverId);
	// 	}
	// 	UserEntity userEntity = userResponse.getData();

	// 	// Update user entity fields
	// 	userEntity.setFullName(updateRequest.getFullName());
	// 	userEntity.setEmail(updateRequest.getEmail());
	// 	userEntity.setContactNo(updateRequest.getContactNo());

	// 	// Save the updated user entity
	// 	userServiceClient.updateUser(userEntity); // Make an API call to update the user

	// 	// Update driver entity fields
	// 	driverEntity.setVehicleNumber(updateRequest.getVehicleNumber());
	// 	driverEntity.setDrivingLicenceNo(updateRequest.getDrivingLicenceNo());
	// 	driverEntity.setVehicleName(updateRequest.getVehicleName());
	// 	driverEntity.setVehicleType(updateRequest.getVehicleType());
	// 	driverEntity.setRcNumber(updateRequest.getRcNumber());
	// 	driverEntity.setAccountHolderName(updateRequest.getAccountHolderName());
	// 	driverEntity.setDriverBankName(updateRequest.getDriverBankName());
	// 	driverEntity.setDriverAccountNo(updateRequest.getDriverAccountNo());
	// 	driverEntity.setDriverIfsc(updateRequest.getDriverIfsc());
	// 	driverEntity.setDriverUpiId(updateRequest.getDriverUpiId());
	// 	driverEntity.setAadharNumber(updateRequest.getAadharNumber());

	// 	// Save the updated driver entity
	// 	driverRepository.save(driverEntity);

	// 	// Prepare response DTO with updated information
	// 	CompleteDriverDetailsResponseDto completeDetails = new CompleteDriverDetailsResponseDto();
	// 	completeDetails.setUserId(userEntity.getUserId());
	// 	completeDetails.setDriverId(driverEntity.getDriverId());
	// 	completeDetails.setFullName(userEntity.getFullName());
	// 	completeDetails.setEmail(userEntity.getEmail());
	// 	completeDetails.setContactNo(userEntity.getContactNo());

	// 	String role = userEntity.getRoles()
	// 			.stream()
	// 			.map(Enum::name)
	// 			.collect(Collectors.joining(", "));
	// 	completeDetails.setRole(role);

	// 	completeDetails.setVehicleNumber(driverEntity.getVehicleNumber());
	// 	completeDetails.setDrivingLicenceNo(driverEntity.getDrivingLicenceNo());
	// 	completeDetails.setVehicleName(driverEntity.getVehicleName());
	// 	completeDetails.setVehicleType(driverEntity.getVehicleType());
	// 	completeDetails.setRcNumber(driverEntity.getRcNumber());

	// 	completeDetails.setAccountHolderName(driverEntity.getAccountHolderName());
	// 	completeDetails.setDriverBankName(driverEntity.getDriverBankName());
	// 	completeDetails.setDriverAccountNo(driverEntity.getDriverAccountNo());
	// 	completeDetails.setDriverIfsc(driverEntity.getDriverIfsc());
	// 	completeDetails.setDriverUpiId(driverEntity.getDriverUpiId());
	// 	completeDetails.setAadharNumber(driverEntity.getAadharNumber());

	// 	return completeDetails;
	// }

	public CommonResponseDto<String> resetDriverPassword(ResetPasswordRequestDTO requestDto) {
        var userResponse = userServiceClient.resetUserPassword(requestDto);
        if (userResponse.getStatusCode() != 200) {
            throw new EntityNotFoundException("User not found with email = " + requestDto.getEmail());
        }        
        return userResponse;
    }
	
}
