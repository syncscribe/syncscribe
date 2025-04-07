package com.example.notify.services;

import com.example.notify.auth.JwtUtil;
import com.example.notify.auth.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Token login(String username, String password) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return jwtUtil.generateToken(auth.getName());
    }
}
