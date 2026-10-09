package com.hotel.saas.hotel_saas_backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HotelRegistrationRequest(

        @NotBlank(message = "Hotel name is required")
        String hotelName,

        @NotBlank(message = "Hotel email is required")
        @Email(message = "Invalid hotel email")
        String hotelEmail,

        String phone,

        String address,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "Currency is required")
        String currency,

        @NotBlank(message = "Timezone is required")
        String timezone,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Owner email is required")
        @Email(message = "Invalid owner email")
        String ownerEmail,

        @NotBlank(message = "Password is required")
        @Size(min = 12, message = "Password must have at least 12 characters")
        String password

) {}

