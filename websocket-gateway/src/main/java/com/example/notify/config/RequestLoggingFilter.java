package com.example.notify.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;


@Slf4j
@Component
public class RequestLoggingFilter implements Filter {
    private static final Set<String> SENSITIVE_FIELDS = Set.of("password", "confirmPassword", "newPassword");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        var wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(wrappedRequest, servletResponse);

        byte[] requestBody = wrappedRequest.getContentAsByteArray();
        if (requestBody.length > 0 && requestBody.length < 10_000) {
            String body = new String(requestBody, StandardCharsets.UTF_8);
            String sanitizedBody = maskSensitiveData(body);
            log.info("Request Body: {}", sanitizedBody);
        } else {
            log.warn("Request Body too large to log ({} bytes)", requestBody.length);
        }
    }

    private String maskSensitiveData(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);
            if (root.isObject()) {
                root.fields().forEachRemaining(entry -> {
                    if (SENSITIVE_FIELDS.contains(entry.getKey())) {
                        ((ObjectNode) root).put(entry.getKey(), "*****");
                    }
                });
                return objectMapper.writeValueAsString(root);
            }
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse JSON request body: {}", e.getMessage());
        }
        return json;
    }
}
