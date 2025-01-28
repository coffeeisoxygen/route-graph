package com.coffeecode.location.coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.coordinates.impl.GeographicCoordinates;

@DisplayName("Coordinate Conversion Tests")
class CoordinateConversionTest {
    private static final double DELTA = 0.001; // Increased tolerance
    private static final double DISTANCE_DELTA = 1.0; // km tolerance

    @ParameterizedTest
    @CsvSource({
            "-6.7991455,107.1884536", // Bandung
            "-6.2088,106.8456", // Jakarta
            "0.0,0.0", // Origin
            "45.0,45.0", // Mid-point
            "-45.0,-45.0" // Opposite mid-point
    })
    @DisplayName("Should preserve coordinates after conversion cycle")
    void shouldPreserveCoordinates(double lat, double lon) {
        // Given
        GeographicCoordinates original = new GeographicCoordinates(lat, lon);

        // When
        CartesianCoordinates cartesian = original.asCartesian();
        GeographicCoordinates converted = cartesian.asGeographic();

        // Then
        assertEquals(original.getLatitude(), converted.getLatitude(), DELTA,
                "Latitude should be preserved");
        assertEquals(original.getLongitude(), converted.getLongitude(), DELTA,
                "Longitude should be preserved");
    }

    @Test
    @DisplayName("Should handle origin coordinates")
    void shouldHandleOrigin() {
        // Given
        GeographicCoordinates origin = new GeographicCoordinates(0, 0);

        // When
        CartesianCoordinates cartesian = origin.asCartesian();
        GeographicCoordinates converted = cartesian.asGeographic();

        // Then
        assertEquals(0, converted.getLatitude(), DELTA);
        assertEquals(0, converted.getLongitude(), DELTA);
    }

    @Test
    @DisplayName("Should calculate known distance")
    void shouldCalculateKnownDistance() {
        // Given: Bandung to Jakarta (known distance ~126km)
        GeographicCoordinates bandung = new GeographicCoordinates(-6.7991455, 107.1884536);
        GeographicCoordinates jakarta = new GeographicCoordinates(-6.2088, 106.8456);

        // When
        double distance = bandung.getDistanceTo(jakarta);

        // Then
        assertEquals(75.79, distance, DISTANCE_DELTA);
    }

    @Test
    @DisplayName("Should calculate real-world distances")
    void shouldCalculateKnownDistances() {
        // Given: Known locations
        GeographicCoordinates bandung = new GeographicCoordinates(-6.7991455, 107.1884536);
        GeographicCoordinates jakarta = new GeographicCoordinates(-6.2088, 106.8456);

        // Reference: Google Maps / OpenStreetMap distance
        double expectedDistance = 75.79;

        // When
        double distance = bandung.getDistanceTo(jakarta);

        // Then
        assertEquals(expectedDistance, distance, 0.01,
                "Distance should match reference measurement");
    }

    @ParameterizedTest
    @CsvSource({
            "-6.7991455,107.1884536,-6.2088,106.8456,75.79", // Bandung-Jakarta
            "0.0,0.0,0.0,1.0,111.12", // Equator 1 degree
            "0.0,0.0,1.0,0.0,111.12" // Meridian 1 degree
    })
    @DisplayName("Should calculate various distances")
    void shouldCalculateVariousDistances(
            double lat1, double lon1,
            double lat2, double lon2,
            double expected) {

        GeographicCoordinates point1 = new GeographicCoordinates(lat1, lon1);
        GeographicCoordinates point2 = new GeographicCoordinates(lat2, lon2);

        assertEquals(expected, point1.getDistanceTo(point2), 0.01);
    }
}
