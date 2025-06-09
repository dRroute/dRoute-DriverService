package com.droute.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoctionDetailsRequestDto {
    private String longitude;
    private String latitude;
    
    private String address;
    
    private String state;
    private String city;
    private String pinCode;
    private String country;
}
