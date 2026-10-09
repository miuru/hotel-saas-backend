
CREATE TABLE room_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    hotel_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    max_occupancy INT NOT NULL,
    base_price DECIMAL(12, 2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_room_types_hotel
        FOREIGN KEY (hotel_id)
        REFERENCES hotels(id),

    CONSTRAINT uk_room_types_hotel_name
        UNIQUE (hotel_id, name),

    CONSTRAINT chk_room_types_occupancy
        CHECK (max_occupancy > 0),

    CONSTRAINT chk_room_types_price
        CHECK (base_price >= 0)
);

CREATE INDEX idx_room_types_hotel_active
    ON room_types(hotel_id, active);
