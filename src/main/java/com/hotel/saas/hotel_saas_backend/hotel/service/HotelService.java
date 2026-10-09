package com.hotel.saas.hotel_saas_backend.hotel.service;

import com.hotel.saas.hotel_saas_backend.hotel.dto.CreateHotelRequest;
import com.hotel.saas.hotel_saas_backend.hotel.dto.CreateHotelResponse;
import com.hotel.saas.hotel_saas_backend.hotel.entity.Hotel;
import com.hotel.saas.hotel_saas_backend.hotel.repository.HotelRepository;
import com.hotel.saas.hotel_saas_backend.security.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CurrentUserService currentUserService;

    public HotelService(HotelRepository hotelRepository, CurrentUserService currentUserService) {
        this.hotelRepository = hotelRepository;
        this.currentUserService = currentUserService;
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


    @Transactional(readOnly = true)
    public CreateHotelResponse getCurrentHotel() {

        Long hotelId = currentUserService.getCurrentHotelId();

        Hotel hotel = hotelRepository
                .findByIdAndActiveTrue(hotelId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Hotel not found"
                        )
                );

        return mapToResponse(hotel);
    }

}
