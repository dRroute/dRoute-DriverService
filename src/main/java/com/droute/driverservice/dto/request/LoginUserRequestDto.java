package com.droute.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUserRequestDto {
    private String emailOrPhone;
    private String password;
    private String role; // This field is used to check if the user is a driver or not. It is not used in the login process.
}
