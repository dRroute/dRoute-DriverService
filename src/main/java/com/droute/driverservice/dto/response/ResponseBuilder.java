package com.droute.driverservice.dto.response;

import java.time.Instant;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    // Successful response
    public static <T> ResponseEntity<CommonResponseDto<T>> success(HttpStatus status,String message, T entity) {
        return ResponseEntity.status(status)
                .body(buildResponse(status, message, entity,  "DRI_"+ status.value()+"_"+status.name()));
    }

    // Failure response
    public static <T> ResponseEntity<CommonResponseDto<T>> failure(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(buildResponse(status, message, null,  "DRI_"+ status.value()+"_"+status.name()));
    }
    // Failure response for Validation
    public static <T> ResponseEntity<CommonResponseDto<T>> failure(HttpStatus status, String message, T entity) {
        return ResponseEntity.status(status)
                .body(buildResponse(status, message, entity,  "DRI_VALIDATION_ERROR"));
    }

    // Core builder
    private static <T> CommonResponseDto<T> buildResponse(HttpStatus status, String message, T entity,  String errorCode) {
        return new CommonResponseDto<>(
                message,
                entity,
                status.value(),
                Instant.now().toString(),
                MDC.get("traceId"), // Assumes Sleuth or custom trace setup
                errorCode
        );
    }
}

