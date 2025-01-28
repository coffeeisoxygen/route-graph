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
    private static final double DELTA = 0.0001;

    @ParameterizedTest
    @CsvSource({
        "-6.7991455,107.1884536,100", // Bandung with elevation
        "-6.2088,106.8456,0",         // Jakarta at sea level
        "0.0,0.0,0",                  // Origin
        "90.0,0.0,0",                 // North Pole
        "-90.0,0.0,0"                 // South Pole
    })
    @DisplayName("Should preserve coordinates after conversion cycle")
    void shouldPreserveCoordinates(double lat, double lon, double elevation) {
        // Given
        GeographicCoordinates original = new GeographicCoordinates(lat, lon, elevation);

        // When
        CartesianCoordinates cartesian = original.asCartesian();
        GeographicCoordinates converted = cartesian.asGeographic();

        // Then
        assertEquals(original.getLatitude(), converted.getLatitude(), DELTA);
        assertEquals(original.getLongitude(), converted.getLongitude(), DELTA);
        assertEquals(original.getElevation(), converted.getElevation(), DELTA);
    }

    @Test
    @DisplayName("Should handle zero coordinates")
    void shouldHandleZeroCoordinates() {
        // Given
        CartesianCoordinates origin = CartesianCoordinates.of(0.0, 0.0, 0.0);

        // When
        GeographicCoordinates geo = origin.asGeographic();

        // Then
        assertEquals(0.0, geo.getLatitude());
        assertEquals(0.0, geo.getLongitude());
    }

    @Test
    @DisplayName("Should calculate real-world distance")
    void shouldCalculateRealWorldDistance() {
        // Bandung to Jakarta with elevation
        GeographicCoordinates bandung = new GeographicCoordinates(-6.7991455, 107.1884536, 768);
        GeographicCoordinates jakarta = new GeographicCoordinates(-6.2088, 106.8456, 8);

        double distance = bandung.getDistanceTo(jakarta);
        assertEquals(126.0, distance, 1.0); // ~126km ±1km
    }
}
