package com.hotel.saas.hotel_saas_backend.auth.service;

import com.hotel.saas.hotel_saas_backend.auth.dto.AuthenticationResponse;
import com.hotel.saas.hotel_saas_backend.auth.dto.LoginRequest;
import com.hotel.saas.hotel_saas_backend.auth.dto.RegisterRequest;
import com.hotel.saas.hotel_saas_backend.hotel.entity.Hotel;
import com.hotel.saas.hotel_saas_backend.hotel.repository.HotelRepository;
import com.hotel.saas.hotel_saas_backend.security.JwtService;
import com.hotel.saas.hotel_saas_backend.user.entity.Role;
import com.hotel.saas.hotel_saas_backend.user.entity.User;
import com.hotel.saas.hotel_saas_backend.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            HotelRepository hotelRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(
            RegisterRequest request) {

        String email =
                request.email().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        Hotel hotel =
                hotelRepository
                        .findById(request.hotelId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Hotel not found"
                                )
                        );

        User user = new User();

        user.setHotel(hotel);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        user.setRole(Role.HOTEL_ADMIN);
        user.setActive(true);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser =
                userRepository.save(user);

        String token =
                jwtService.generateToken(savedUser);

        return buildResponse(
                savedUser,
                token
        );
    }

    public AuthenticationResponse login(
            LoginRequest request) {

        String email =
                request.email().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "User not found"
                                )
                        );

        String token =
                jwtService.generateToken(user);

        return buildResponse(
                user,
                token
        );
    }

    private AuthenticationResponse buildResponse(
            User user,
            String token) {

        return new AuthenticationResponse(
                token,
                "Bearer",
                user.getId(),
                user.getHotel().getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
