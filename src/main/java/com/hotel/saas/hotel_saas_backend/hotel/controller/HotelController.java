package com.hotel.saas.hotel_saas_backend.hotel.controller;

import com.hotel.saas.hotel_saas_backend.hotel.dto.CreateHotelRequest;
import com.hotel.saas.hotel_saas_backend.hotel.dto.CreateHotelResponse;
import com.hotel.saas.hotel_saas_backend.hotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<CreateHotelResponse> createHotel(
            @Valid @RequestBody CreateHotelRequest request) {

        CreateHotelResponse response =
                hotelService.createHotel(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateHotelResponse> getHotel(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                hotelService.getHotel(id)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<CreateHotelResponse> getCurrentHotel() {

        return ResponseEntity.ok(
                hotelService.getCurrentHotel()
        );
    }

}
