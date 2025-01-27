package com.coffeecode.domain.values.location;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

/**
 * Represents a distance measurement in meters. This class is immutable and
 * ensures that the distance value is non-negative.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * Distance distance = new Distance(100.0);
 * System.out.println(distance.getValue()); // 100.0
 * }
 * </pre>
 *
 * <p>
 * Annotations used:
 * </p>
 * <ul>
 * <li>{@code @Getter} - Generates getter methods for all fields.</li>
 * <li>{@code @ToString} - Generates a {@code toString()} method.</li>
 * <li>{@code @EqualsAndHashCode} - Generates {@code equals()} and
 * {@code hashCode()} methods.</li>
 * </ul>
 *
 * @throws ValidationException if the distance value is negative.
 */
@Value
public class Distance {
    // Consider moving constants to OperationalLimits
    private static final double MIN_DISTANCE = 0.0;
    private static final double MAX_DISTANCE = 1000000.0;

    // Consider renaming to meters for clarity
    double metersValue; // in meters

    private Distance(double value) {
        validateDistance(value);
        this.metersValue = value;
    }

    private void validateDistance(double value) {
        if (value < MIN_DISTANCE || value > MAX_DISTANCE) {
            throw ValidationException.invalidRange("Distance",
                    MIN_DISTANCE, MAX_DISTANCE);
        }
    }

    // Add documentation for factory methods
    /**
     * Creates a new Distance instance with the specified value in meters.
     *
     * @param meters the distance in meters
     * @return a new Distance instance
     * @throws ValidationException if the value is invalid
     */
    public static Distance ofMeters(double meters) {
        return new Distance(meters);
    }

    public static Distance ofKilometers(double kilometers) {
        return new Distance(kilometers * 1000);
    }

    public double getMetersValue() {
        return metersValue;
    }
}
