package com.hotel.saas.hotel_saas_backend.auth.dto;

public record AuthenticationResponse(

        String accessToken,
        String tokenType,
        Long userId,
        Long hotelId,
        String firstName,
        String lastName,
        String email,
        String role

) {
}
