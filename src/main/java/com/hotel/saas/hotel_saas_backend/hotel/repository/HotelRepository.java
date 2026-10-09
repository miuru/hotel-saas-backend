package com.hotel.saas.hotel_saas_backend.hotel.repository;

import com.hotel.saas.hotel_saas_backend.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    boolean existsByEmail(String email);

    Optional<Hotel> findByIdAndActiveTrue(Long id);
}