package com.hotel.saas.hotel_saas_backend.hotel.service;

import com.hotel.saas.hotel_saas_backend.hotel.dto.CreateHotelRequest;
import com.hotel.saas.hotel_saas_backend.hotel.dto.CreateHotelResponse;
import com.hotel.saas.hotel_saas_backend.hotel.entity.Hotel;
import com.hotel.saas.hotel_saas_backend.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public CreateHotelResponse createHotel(CreateHotelRequest request) {

        Hotel hotel = new Hotel();

        hotel.setName(request.name());
        hotel.setEmail(request.email());
        hotel.setPhone(request.phone());
        hotel.setAddress(request.address());
        hotel.setCity(request.city());
        hotel.setCountry(request.country());

        hotel.setCurrency(
                request.currency() != null
                        ? request.currency()
                        : "LKR"
        );

        hotel.setTimezone(
                request.timezone() != null
                        ? request.timezone()
                        : "Asia/Colombo"
        );

        hotel.setActive(true);
        hotel.setCreatedAt(LocalDateTime.now());
        hotel.setUpdatedAt(LocalDateTime.now());

        Hotel savedHotel = hotelRepository.save(hotel);

        return mapToResponse(savedHotel);
    }

    public CreateHotelResponse getHotel(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hotel not found"));

        return mapToResponse(hotel);
    }

    private CreateHotelResponse mapToResponse(Hotel hotel) {

        return new CreateHotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getEmail(),
                hotel.getPhone(),
                hotel.getAddress(),
                hotel.getCity(),
                hotel.getCountry(),
                hotel.getCurrency(),
                hotel.getTimezone(),
                hotel.getActive(),
                hotel.getCreatedAt(),
                hotel.getUpdatedAt()
        );
    }
}
