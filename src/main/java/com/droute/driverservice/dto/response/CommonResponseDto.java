package com.droute.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponseDto<T> {

    private String message;
    private T data;
    private int statusCode;
    private String timestamp;
    private String traceId;
    private String errorCode;
}
