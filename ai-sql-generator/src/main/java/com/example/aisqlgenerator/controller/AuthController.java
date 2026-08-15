package com.example.aisqlgenerator.controller;

import com.example.aisqlgenerator.dto.LoginRequest;
import com.example.aisqlgenerator.dto.RegisterRequest;
import com.example.aisqlgenerator.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Map<String, String> register(
            @RequestBody RegisterRequest request) {

        authService.register(request);

        return Map.of(
                "message",
                "Registration successful"
        );
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginRequest request) {

        String token =
                authService.login(request);

        return Map.of(
                "token",
                token
        );
    }
}