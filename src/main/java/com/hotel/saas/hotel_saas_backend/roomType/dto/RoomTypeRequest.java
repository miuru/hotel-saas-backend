package com.hotel.saas.hotel_saas_backend.roomType.dto;


import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record RoomTypeRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull
        @Min(1)
        Integer maxOccupancy,

        @NotNull
        @DecimalMin("0.00")
        @Digits(integer = 10, fraction = 2)
        BigDecimal basePrice

) {}

