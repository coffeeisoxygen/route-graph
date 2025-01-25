package com.coffeecode.core.model;

import java.util.UUID;

import com.coffeecode.util.CoordinateValidator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
