package com.example.notify.config;

import com.example.notify.auth.Swagger;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return Swagger.openAPI("User Service", "User Service API");
    }
}
