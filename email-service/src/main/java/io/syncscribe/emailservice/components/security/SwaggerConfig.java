package io.syncscribe.userservice.components.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.syncscribe.common.auth.Swagger;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return Swagger.openAPI("Email Service", "Email Service");
    }
}
