package io.syncscribe.fileservice.components.security;

import io.syncscribe.common.auth.ZitadelOpaqueTokenIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OpaqueTokenIntrospector introspector;

    public SecurityConfig(OpaqueTokenIntrospector introspector) {
        this.introspector = introspector;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth
                        -> auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/health").permitAll()
                        .anyRequest().authenticated());
        http.oauth2ResourceServer(configurer
                -> configurer.jwt(Customizer.withDefaults()));
        //http.oauth2ResourceServer(configurer
        //        -> configurer.opaqueToken(token
        //        -> token.introspector(introspector())));
        return http.build();
    }

    private OpaqueTokenIntrospector introspector() {
        return new ZitadelOpaqueTokenIntrospector(introspector);
    }
}
