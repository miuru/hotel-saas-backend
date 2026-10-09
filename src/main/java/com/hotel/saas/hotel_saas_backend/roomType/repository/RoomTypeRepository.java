package com.hotel.saas.hotel_saas_backend.roomType.repository;

import com.hotel.saas.hotel_saas_backend.roomType.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository
        extends JpaRepository<RoomType, Long> {

    /**
     * Returns all active room types belonging to
     * a particular hotel, ordered alphabetically.
     */
    List<RoomType> findByHotel_IdAndActiveTrueOrderByNameAsc(
            Long hotelId
    );

    /**
     * Finds one active room type belonging to a
     * particular hotel.
     *
     * Both the room type ID and hotel ID must match.
     */
    Optional<RoomType> findByIdAndHotel_IdAndActiveTrue(
            Long id,
            Long hotelId
    );

    /**
     * Checks whether a room type name already exists
     * within the same hotel.
     */
    boolean existsByHotel_IdAndNameIgnoreCase(
            Long hotelId,
            String name
    );

    /**
     * Checks whether another room type has the same
     * name during an update operation.
     */
    boolean existsByHotel_IdAndNameIgnoreCaseAndIdNot(
            Long hotelId,
            String name,
            Long id
    );
}

