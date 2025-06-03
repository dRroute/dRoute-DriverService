package com.droute.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Lombok annotation for getters/setters
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentUploadRequestDto {
    private Long driverId;
    private String documentName;
}
