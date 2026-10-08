package com.hotel.saas.hotel_saas_backend.hotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateHotelRequest(

        @NotBlank(message = "Hotel name is required")
        @Size(max = 150)
        String name,

        @Email(message = "Invalid email address")
        String email,

        String phone,

        String address,

        String city,

        String country,

        String currency,

        String timezone
) {
}