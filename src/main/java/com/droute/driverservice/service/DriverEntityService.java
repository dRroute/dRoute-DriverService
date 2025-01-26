package com.droute.driverservice.service;


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

import com.droute.driverservice.dto.CommonResponseDto;
import com.droute.driverservice.dto.RegisterUserRequestDto;
import com.droute.driverservice.entity.UserEntity;
import com.droute.driverservice.exception.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DriverEntityService {

	@Autowired
	private RestTemplate restTemplate;

	private final String commonUserEndPoint = "http://localhost:8080/droute-user-service/api/user";
	
	
	private CommonResponseDto<UserEntity> handleErrorResponse(String responseBody) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();

	        // Try to deserialize into CommonResponseDto first
	        CommonResponseDto<UserEntity> commonResponse = objectMapper.readValue(
	            responseBody, new TypeReference<CommonResponseDto<UserEntity>>() {}
	        );

	        // If successful, return the CommonResponseDto
	        return commonResponse;

	    } catch (JsonProcessingException e) {
	        // If deserialization to CommonResponseDto fails, try ErrorMessage format
	        try {
	            ErrorMessage errorMessage = new ObjectMapper().readValue(responseBody, ErrorMessage.class);

	            // Create a CommonResponseDto for the error message
	            return new CommonResponseDto<>(errorMessage.getMessageDetails(), null);
	        } catch (JsonProcessingException ex) {
	            // If both deserialization attempts fail, return a generic error message
	            return new CommonResponseDto<>("Error parsing response", null);
	        }
	    }
	}

	public ResponseEntity<CommonResponseDto<UserEntity>> loginDriver(String emailOrPhone, String password) {
		String url = commonUserEndPoint + "/auth/login?emailOrPhone={emailOrPhone}&password={password}";

		try {
			// Making the GET request
			ResponseEntity<CommonResponseDto<UserEntity>> response = restTemplate.exchange(url, HttpMethod.GET, null, // No
																														// request
																														// body
					new ParameterizedTypeReference<CommonResponseDto<UserEntity>>() {
					}, emailOrPhone, password);

			// Return the ResponseEntity directly
			return response;

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			// Handle error responses (e.g., 400 or 500 HTTP errors)
			String responseBody = ex.getResponseBodyAsString();
			CommonResponseDto<UserEntity> errorResponse = handleErrorResponse(responseBody);
			return ResponseEntity.status(ex.getStatusCode()).body(errorResponse); // Return ResponseEntity with error
		} catch (Exception ex) {
			// General exception handling
			CommonResponseDto<UserEntity> errorResponse = new CommonResponseDto<>("Internal server error", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // Return ResponseEntity
																								// with error
		}
	}

	

	public ResponseEntity<CommonResponseDto<UserEntity>> registerDriver(RegisterUserRequestDto driverDetails) {
		String url = commonUserEndPoint + "/";

		try {
			// Making the POST request
			ResponseEntity<CommonResponseDto<UserEntity>> response = restTemplate.exchange(
			        url,                   // The URL to which the POST request is sent
			        HttpMethod.POST,       // HTTP method (POST)
			        new HttpEntity<>(driverDetails),  // Request body (driverDetails) wrapped in HttpEntity
			        new ParameterizedTypeReference<CommonResponseDto<UserEntity>>() {}  // Type reference for response body
			);


			// Return the ResponseEntity directly
			return response;

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			// Handle error responses (e.g., 400 or 500 HTTP errors)
			String responseBody = ex.getResponseBodyAsString();
			CommonResponseDto<UserEntity> errorResponse = handleErrorResponse(responseBody);
			return ResponseEntity.status(ex.getStatusCode()).body(errorResponse); // Return ResponseEntity with error
		} catch (Exception ex) {
			// General exception handling
			CommonResponseDto<UserEntity> errorResponse = new CommonResponseDto<>("Internal server error", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // Return ResponseEntity
																								// with error
		}
	}


}
