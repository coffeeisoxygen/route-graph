package com.coffeecode.domain.objects;

import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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

    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90 degrees!")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90 degrees!")
    private final double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180 degrees!")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180 degrees!")
    private final double longitude;

    private Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Coordinate of(double latitude, double longitude) {
        Coordinate coordinate = new Coordinate(latitude, longitude);
        return ValidationUtils.validate(coordinate);
    }
}
