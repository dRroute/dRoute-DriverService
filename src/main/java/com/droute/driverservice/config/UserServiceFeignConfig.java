package com.droute.driverservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.droute.driverservice.feign.error.decoder.CustomFeignErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class UserServiceFeignConfig {
    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
