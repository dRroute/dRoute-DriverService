package com.droute.driverservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("dRoute-Driver API")
                        .version("1.0")
                        .description("API documentation for dRoute Driver service application")
                );
                // .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                // .components(new io.swagger.v3.oas.models.Components()
                //         .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                //                 .name("Bearer Authentication")
                //                 .type(SecurityScheme.Type.HTTP)
                //                 .scheme("bearer")
                //                 .bearerFormat("JWT") // This tells Swagger to use JWT tokens
                //         )
                // )
    }
}
