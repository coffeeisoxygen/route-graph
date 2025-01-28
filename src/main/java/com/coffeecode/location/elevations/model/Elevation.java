package com.coffeecode.location.elevations.model;

import lombok.Builder;
import lombok.Value;

/**
 * Represents elevation data with its source type.
 * Elevation is stored in meters and validated against physical bounds.
 */
@Value
@Builder
public class Elevation {
    private static final double MIN_ELEVATION = -500.0; // Dead Sea
    private static final double MAX_ELEVATION = 9000.0; // Mount Everest + margin

    double meters;
    Type type;

    /**
     * Creates elevation data from API response
     */
    public static Elevation fromApi(double meters) {
        validateElevation(meters);
        return new Elevation(meters, Type.API);
    }

    /**
     * Creates default elevation (sea level)
     */
    public static Elevation defaultValue() {
        return new Elevation(0.0, Type.DEFAULT);
    }

    /**
     * Creates elevation from manual input
     */
    public static Elevation manual(double meters) {
        validateElevation(meters);
        return new Elevation(meters, Type.MANUAL);
    }

    private static void validateElevation(double meters) {
        if (meters < MIN_ELEVATION || meters > MAX_ELEVATION) {
            throw new IllegalArgumentException(
                    String.format("Elevation must be between %.1f and %.1f meters",
                            MIN_ELEVATION, MAX_ELEVATION));
        }
    }

    public enum Type {
        API, // From elevation API
        DEFAULT, // Default value (sea level)
        MANUAL // Manually set
    }
}
