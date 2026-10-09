package com.hotel.saas.hotel_saas_backend.auth.controller;

import com.hotel.saas.hotel_saas_backend.auth.dto.AuthenticationResponse;
import com.hotel.saas.hotel_saas_backend.auth.dto.HotelRegistrationRequest;
import com.hotel.saas.hotel_saas_backend.auth.service.HotelOnboardingService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/onboarding")
public class HotelOnboardingController {

    private final HotelOnboardingService onboardingService;

    public HotelOnboardingController(
            HotelOnboardingService onboardingService) {

        this.onboardingService = onboardingService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody HotelRegistrationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(onboardingService.registerHotel(request));
    }
}

