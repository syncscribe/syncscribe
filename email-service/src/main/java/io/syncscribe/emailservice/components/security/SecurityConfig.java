package io.syncscribe.emailservice.components.security;

import io.syncscribe.common.auth.ZitadelOpaqueTokenIntrospector;
import io.syncscribe.common.auth.ZitadelRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    public SecurityConfig(OpaqueTokenIntrospector opaqueTokenIntrospector) {
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth
                -> auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer(configurer
                -> configurer.opaqueToken(a -> a.introspector(introspector())));
        return http.build();
    }

    private OpaqueTokenIntrospector introspector() {
        return new ZitadelOpaqueTokenIntrospector(opaqueTokenIntrospector);
    }
}
