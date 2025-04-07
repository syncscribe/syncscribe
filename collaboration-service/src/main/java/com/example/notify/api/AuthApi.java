package com.example.notify.api;


import com.example.notify.models.LoginRequest;
import com.example.notify.auth.Token;
import com.example.notify.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {
    private final AuthService authService;

    public AuthApi(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequest request) {
        return authService.login(request.username(), request.password());
    }
}
