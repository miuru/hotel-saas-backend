package com.hotel.saas.hotel_saas_backend.hotel.dto;

import java.time.LocalDateTime;

public record CreateHotelResponse(

        Long id,
        String name,
        String email,
        String phone,
        String address,
        String city,
        String country,
        String currency,
        String timezone,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
