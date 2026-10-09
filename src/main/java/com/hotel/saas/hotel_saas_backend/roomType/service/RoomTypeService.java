package com.hotel.saas.hotel_saas_backend.roomType.service;

import com.hotel.saas.hotel_saas_backend.hotel.entity.Hotel;
import com.hotel.saas.hotel_saas_backend.hotel.repository.HotelRepository;
import com.hotel.saas.hotel_saas_backend.roomType.dto.RoomTypeRequest;
import com.hotel.saas.hotel_saas_backend.roomType.dto.RoomTypeResponse;
import com.hotel.saas.hotel_saas_backend.roomType.entity.RoomType;
import com.hotel.saas.hotel_saas_backend.roomType.repository.RoomTypeRepository;
import com.hotel.saas.hotel_saas_backend.security.CurrentUserService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;
    private final CurrentUserService currentUserService;

    public RoomTypeService(
            RoomTypeRepository roomTypeRepository,
            HotelRepository hotelRepository,
            CurrentUserService currentUserService) {

        this.roomTypeRepository = roomTypeRepository;
        this.hotelRepository = hotelRepository;
        this.currentUserService = currentUserService;
    }

    /**
     * Creates a room type for the currently authenticated hotel.
     * <p>
     * The hotel ID is obtained from Spring Security rather
     * than accepted from the HTTP request.
     *
     * @param request validated room type details
     * @return the newly created room type
     */
    @Transactional
    public RoomTypeResponse create(RoomTypeRequest request) {

        Long hotelId = currentUserService.getCurrentHotelId();
        String name = request.name().trim();

        if (roomTypeRepository
                .existsByHotel_IdAndNameIgnoreCase(hotelId, name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Room type name already exists"
            );
        }

        Hotel hotel = hotelRepository
                .findByIdAndActiveTrue(hotelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Hotel not found"
                ));

        RoomType roomType = new RoomType();
        roomType.setHotel(hotel);
        roomType.setName(name);
        roomType.setDescription(request.description());
        roomType.setMaxOccupancy(request.maxOccupancy());
        roomType.setBasePrice(request.basePrice());
        roomType.setActive(true);

        RoomType saved = roomTypeRepository.save(roomType);

        return toResponse(saved);
    }

    /**
     * Retrieves all active room types belonging to the
     * authenticated hotel.
     *
     * @return list of room types for the current tenant
     */
    @Transactional(readOnly = true)
    public List<RoomTypeResponse> getAll() {

        Long hotelId = currentUserService.getCurrentHotelId();

        return roomTypeRepository
                .findByHotel_IdAndActiveTrueOrderByNameAsc(hotelId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Retrieves one active room type belonging to the
     * authenticated hotel.
     * <p>
     * Returns 404 if the record does not exist or
     * belongs to another hotel.
     *
     * @param id room type identifier
     * @return room type details
     */
    @Transactional(readOnly = true)
    public RoomTypeResponse getById(Long id) {

        Long hotelId = currentUserService.getCurrentHotelId();

        RoomType roomType = roomTypeRepository
                .findByIdAndHotel_IdAndActiveTrue(id, hotelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Room type not found"
                ));

        return toResponse(roomType);
    }

    /**
     * Updates an existing active room type.
     * <p>
     * The update is permitted only when the room type
     * belongs to the authenticated hotel.
     *
     * @param id      room type identifier
     * @param request updated room type details
     * @return updated room type
     */
    @Transactional
    public RoomTypeResponse update(
            Long id,
            RoomTypeRequest request) {

        Long hotelId = currentUserService.getCurrentHotelId();

        RoomType roomType = roomTypeRepository
                .findByIdAndHotel_IdAndActiveTrue(id, hotelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Room type not found"
                ));

        String name = request.name().trim();

        if (roomTypeRepository
                .existsByHotel_IdAndNameIgnoreCaseAndIdNot(
                        hotelId, name, id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Room type name already exists"
            );
        }

        roomType.setName(name);
        roomType.setDescription(request.description());
        roomType.setMaxOccupancy(request.maxOccupancy());
        roomType.setBasePrice(request.basePrice());

        return toResponse(roomTypeRepository.save(roomType));
    }

    /**
     * Soft-deletes a room type by marking it inactive.
     * <p>
     * The database record remains available for
     * historical reporting and future reservations.
     *
     * @param id room type identifier
     */
    @Transactional
    public void deactivate(Long id) {

        Long hotelId = currentUserService.getCurrentHotelId();

        RoomType roomType = roomTypeRepository
                .findByIdAndHotel_IdAndActiveTrue(id, hotelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Room type not found"
                ));

        roomType.setActive(false);
    }

    /**
     * Converts a RoomType entity into a response DTO.
     * <p>
     * This prevents exposing JPA entities directly
     * through REST API responses.
     *
     * @param roomType persisted room type entity
     * @return response DTO
     */
    private RoomTypeResponse toResponse(RoomType roomType) {

        return new RoomTypeResponse(
                roomType.getId(),
                roomType.getName(),
                roomType.getDescription(),
                roomType.getMaxOccupancy(),
                roomType.getBasePrice(),
                roomType.getActive(),
                roomType.getCreatedAt(),
                roomType.getUpdatedAt()
        );
    }
}

