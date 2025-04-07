package com.example.notify.config;

import com.example.notify.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jwt {
    private final String secretKey;
    private final long jwtExpiration;

    public Jwt(@Value("${security.jwt.secret-key}") String secretKey,
               @Value("${security.jwt.expiration-time}") long jwtExpiration) {
        this.secretKey = secretKey;
        this.jwtExpiration = jwtExpiration;
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secretKey, jwtExpiration);
    }
}
