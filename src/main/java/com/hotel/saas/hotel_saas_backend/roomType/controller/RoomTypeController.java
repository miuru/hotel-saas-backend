package com.hotel.saas.hotel_saas_backend.roomType.controller;

import com.hotel.saas.hotel_saas_backend.roomType.dto.RoomTypeRequest;
import com.hotel.saas.hotel_saas_backend.roomType.dto.RoomTypeResponse;
import com.hotel.saas.hotel_saas_backend.roomType.service.RoomTypeService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    /**
     * Creates a room type for the authenticated hotel.
     *
     * Only hotel administrators and managers can create
     * room types.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('HOTEL_ADMIN', 'MANAGER')")
    public ResponseEntity<RoomTypeResponse> create(
            @Valid @RequestBody RoomTypeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roomTypeService.create(request));
    }

    /**
     * Retrieves all active room types for the
     * currently authenticated hotel.
     */
    @GetMapping
    public ResponseEntity<List<RoomTypeResponse>> getAll() {

        return ResponseEntity.ok(roomTypeService.getAll());
    }

    /**
     * Retrieves one active room type.
     *
     * Returns 404 when the room type does not belong
     * to the authenticated hotel.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(roomTypeService.getById(id));
    }

    /**
     * Updates an existing room type.
     *
     * Only hotel administrators and managers can update
     * room types.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HOTEL_ADMIN', 'MANAGER')")
    public ResponseEntity<RoomTypeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RoomTypeRequest request) {

        return ResponseEntity.ok(
                roomTypeService.update(id, request)
        );
    }

    /**
     * Deactivates a room type without physically
     * deleting the database record.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HOTEL_ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long id) {

        roomTypeService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}

