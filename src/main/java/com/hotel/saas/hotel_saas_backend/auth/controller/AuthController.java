package com.hotel.saas.hotel_saas_backend.auth.controller;

import com.hotel.saas.hotel_saas_backend.auth.dto.AuthenticationResponse;
import com.hotel.saas.hotel_saas_backend.auth.dto.LoginRequest;
import com.hotel.saas.hotel_saas_backend.auth.dto.RegisterRequest;
import com.hotel.saas.hotel_saas_backend.auth.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>
    register(
            @Valid
            @RequestBody
            RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        authService.register(request)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>
    login(
            @Valid
            @RequestBody
            LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
