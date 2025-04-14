package io.syncscribe.gateway.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fallback")
public class FallbackEndpoint {

    @GetMapping("/file")
    public ResponseEntity<String> fileFallback() {
        return ResponseEntity.ok("File service is temporarily unavailable.");
    }

    @GetMapping("/email")
    public ResponseEntity<String> emailFallback() {
        return ResponseEntity.ok("Email service is temporarily unavailable.");
    }
}
