package com.droute.driverservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.droute.driverservice.dto.UserEntity;
import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.ResponseBuilder;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<CommonResponseDto<UserEntity>> entityNotFoundException(EntityNotFoundException exception) {

		logger.error(exception.getMessage());

		return ResponseBuilder.failure(HttpStatus.NOT_FOUND, exception.getMessage());

	}

	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<CommonResponseDto<UserEntity>> entityAlreadyExistException(
			EntityAlreadyExistsException exception) {

		logger.error(exception.getMessage());

		return ResponseBuilder.failure(HttpStatus.CONFLICT, exception.getMessage());

	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<CommonResponseDto<UserEntity>> handleBadRequest(BadRequestException exception) {
		logger.error("Bad Request: " + exception.getMessage(), exception);
		return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<String> handleGenericUserService(UserServiceException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

	@Override
	protected ResponseEntity handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			org.springframework.http.HttpStatusCode status,
			WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Validation failed", errors);
	}

}
