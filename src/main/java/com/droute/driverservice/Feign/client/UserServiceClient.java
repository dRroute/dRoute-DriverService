package com.droute.driverservice.Feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.droute.driverservice.dto.UserEntity;
import com.droute.driverservice.dto.request.LoginUserRequestDto;
import com.droute.driverservice.dto.request.RegisterUserRequestDto;
import com.droute.driverservice.dto.response.CommonResponseDto;

@Component
@FeignClient(name = "droute-cloud-gateway", path = "/api/user")
// @FeignClient(name = "droute-user-service", path = "/api/user")
public interface UserServiceClient {
    
    @PostMapping("/auth/login")
    CommonResponseDto<UserEntity> loginUser(@RequestBody LoginUserRequestDto loginRequest);

    @PostMapping("/")
    CommonResponseDto<UserEntity> registerUser(@RequestBody RegisterUserRequestDto registerRequest);
}
