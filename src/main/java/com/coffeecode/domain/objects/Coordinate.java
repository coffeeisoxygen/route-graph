package com.coffeecode.domain.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a geographic coordinate with latitude and longitude values. This
 * class is immutable and ensures valid coordinate ranges.
 *
 * <p>
 * Example usage:</p>
 * <pre>{@code
 * Coordinate coordinate = Coordinate.of(40.7128, -74.0060);
 * }</pre>
 *
 * <p>
 * Annotations used:</p>
 * <ul>
 * <li>{@code @Getter} - Generates getter methods for all fields.</li>
 * <li>{@code @ToString} - Generates a toString method.</li>
 * <li>{@code @EqualsAndHashCode} - Generates equals and hashCode methods.</li>
 * </ul>
 *
 * @throws IllegalArgumentException if latitude is not between -90 and 90
 * degrees
 * @throws IllegalArgumentException if longitude is not between -180 and 180
 * degrees
 */
@Getter
@ToString
@EqualsAndHashCode
public class Coordinate {

    private final double latitude;
    private final double longitude;

    private Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Coordinate of(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees!");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees!");
        }
        return new Coordinate(latitude, longitude);
    }
}
