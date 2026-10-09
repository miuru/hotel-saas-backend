package com.hotel.saas.hotel_saas_backend.roomType.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RoomTypeResponse(
        Long id,
        String name,
        String description,
        Integer maxOccupancy,
        BigDecimal basePrice,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
