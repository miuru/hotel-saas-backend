package com.hotel.saas.hotel_saas_backend.auth.service;

import com.hotel.saas.hotel_saas_backend.auth.dto.AuthenticationResponse;
import com.hotel.saas.hotel_saas_backend.auth.dto.HotelRegistrationRequest;
import com.hotel.saas.hotel_saas_backend.hotel.entity.Hotel;
import com.hotel.saas.hotel_saas_backend.hotel.repository.HotelRepository;
import com.hotel.saas.hotel_saas_backend.security.JwtService;
import com.hotel.saas.hotel_saas_backend.user.entity.Role;
import com.hotel.saas.hotel_saas_backend.user.entity.User;
import com.hotel.saas.hotel_saas_backend.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@Service
public class HotelOnboardingService {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public HotelOnboardingService(
            HotelRepository hotelRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthenticationResponse registerHotel(
            HotelRegistrationRequest request) {

        String ownerEmail = request.ownerEmail()
                .trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(ownerEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Owner email already registered"
            );
        }

        Hotel hotel = new Hotel();

        hotel.setName(request.hotelName());
        hotel.setEmail(request.hotelEmail());
        hotel.setPhone(request.phone());
        hotel.setAddress(request.address());
        hotel.setCity(request.city());
        hotel.setCountry(request.country());
        hotel.setCurrency(request.currency());
        hotel.setTimezone(request.timezone());
        hotel.setActive(true);

        Hotel savedHotel = hotelRepository.save(hotel);

        User owner = new User();

        owner.setHotel(savedHotel);
        owner.setFirstName(request.firstName());
        owner.setLastName(request.lastName());
        owner.setEmail(ownerEmail);
        owner.setPassword(
                passwordEncoder.encode(request.password())
        );
        owner.setRole(Role.HOTEL_ADMIN);
        owner.setActive(true);

        User savedOwner = userRepository.save(owner);

        String token = jwtService.generateToken(savedOwner);

        return new AuthenticationResponse(
                token,
                "Bearer",
                savedOwner.getId(),
                savedHotel.getId(),
                savedOwner.getFirstName(),
                savedOwner.getLastName(),
                savedOwner.getEmail(),
                savedOwner.getRole().name()
        );
    }
}

