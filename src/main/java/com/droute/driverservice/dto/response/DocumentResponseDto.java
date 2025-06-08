package com.droute.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponseDto {
    private Long documentId;  
    private String documentName;
    private String documentType;
    private String documentUrl;
   
}
