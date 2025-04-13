package io.syncscribe.fileservice.components.remote;

import feign.Client;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients
public class FeignConfig {
    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Bean
    public RequestInterceptor bearerAuthRequestInterceptor() {
        return requestTemplate -> {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();

            if (authentication != null && authentication.getCredentials() instanceof String token) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }

    @Bean
    public Client feignClient(okhttp3.OkHttpClient okHttpClient) {
        return new feign.okhttp.OkHttpClient(okHttpClient);
    }
}
