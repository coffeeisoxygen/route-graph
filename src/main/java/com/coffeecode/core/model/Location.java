package com.coffeecode.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

import com.coffeecode.core.util.CoordinateValidator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {

    @EqualsAndHashCode.Include
    private UUID idLocation;
    private String name;
    private double longitude;
    private double latitude;

    // Factory method for creating new location with generated UUID
    public static Location createNew(String name, double longitude, double latitude) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (!CoordinateValidator.isValidCoordinate(latitude, longitude)) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }
        return Location.builder()
                .idLocation(UUID.randomUUID())
                .name(name)
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
}
