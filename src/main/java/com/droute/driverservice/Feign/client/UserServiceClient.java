package com.droute.driverservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.droute.driverservice.dto.UserEntity;
import com.droute.driverservice.dto.request.LoginUserRequestDto;
import com.droute.driverservice.dto.request.RegisterUserRequestDto;
import com.droute.driverservice.dto.request.ResetPasswordRequestDTO;
import com.droute.driverservice.dto.response.CommonResponseDto;

@Component
@FeignClient(name = "droute-cloud-gateway", path = "/api/user")
// @FeignClient(name = "droute-user-service", path = "/api/user")
public interface UserServiceClient {

   
    @PostMapping("/auth/login")
    CommonResponseDto<UserEntity> loginUser(@RequestBody LoginUserRequestDto loginRequest);

    @GetMapping("/{email}")
    CommonResponseDto<UserEntity> getUserByEmail(@PathVariable("email") String email);

    @PostMapping("/")
    CommonResponseDto<UserEntity> registerUser(@RequestBody RegisterUserRequestDto registerRequest);

    @GetMapping("/{userId}")
    CommonResponseDto<UserEntity> getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/auth/reset-password")
    CommonResponseDto<String> resetUserPassword(@RequestBody ResetPasswordRequestDTO requestDTO);

   
}
