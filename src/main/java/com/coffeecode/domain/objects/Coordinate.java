package com.coffeecode.domain.objects;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a geographic coordinate with latitude and longitude values. Uses
 * validation annotations to ensure valid coordinate ranges.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Coordinate {

    private final double latitude;
    private final double longitude;

    private Coordinate(double latitude, double longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Coordinate of(double latitude, double longitude) {
        return new Coordinate(latitude, longitude);
    }

    private static void validateLatitude(double latitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new ValidationException("Latitude must be between -90 and 90 degrees");
        }
    }

    private static void validateLongitude(double longitude) {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new ValidationException("Longitude must be between -180 and 180 degrees");
        }
    }
}
