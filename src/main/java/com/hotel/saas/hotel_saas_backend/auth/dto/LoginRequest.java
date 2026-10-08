package com.hotel.saas.hotel_saas_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password

) {
}
