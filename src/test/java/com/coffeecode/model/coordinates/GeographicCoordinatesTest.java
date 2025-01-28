package com.coffeecode.model.coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.coordinates.impl.GeographicCoordinates;

@DisplayName("Geographic Coordinates Tests")
class GeographicCoordinatesTest {

    private static final double DISTANCE_TOLERANCE = 0.05; // 5% tolerance

    @ParameterizedTest
    @CsvSource({
            // lat1, lon1, lat2, lon2, expected distance (km)
            "52.2297,   21.0122,   41.9028,   12.4964,   1320.0", // Warsaw-Rome
            "51.5074,   -0.1278,   48.8566,   2.3522,    344.0", // London-Paris
            "-6.2088,   106.8456,  -6.1751,   106.8272,  4.26", // Jakarta-Tangerang (corrected)
            "0.0,       0.0,       0.0,       0.0,       0.0" // Same point
    })
    @DisplayName("Should calculate valid distances between cities")
    void testGetDistanceTo_ValidCoordinates(double lat1, double lon1,
            double lat2, double lon2,
            double expectedDistance) {
        // Given
        GeographicCoordinates coord1 = new GeographicCoordinates(lat1, lon1);
        GeographicCoordinates coord2 = new GeographicCoordinates(lat2, lon2);

        // When
        double distance = coord1.getDistanceTo(coord2);

        // Then
        double tolerance = expectedDistance * DISTANCE_TOLERANCE;
        assertEquals(expectedDistance, distance, tolerance,
                String.format("Distance between (%.4f,%.4f) and (%.4f,%.4f)",
                        lat1, lon1, lat2, lon2));
    }

    @Test
    @DisplayName("Should validate latitude bounds")
    void testValidateLatitude() {
        @SuppressWarnings("unused")
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> new GeographicCoordinates(91.0, 0.0));
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> new GeographicCoordinates(-91.0, 0.0));
    }

    @Test
    @DisplayName("Should validate longitude bounds")
    void testValidateLongitude() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> new GeographicCoordinates(0.0, 181.0));
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> new GeographicCoordinates(0.0, -181.0));
    }

    @Test
    @DisplayName("Should return zero for same coordinates")
    void testGetDistanceTo_SameCoordinates() {
        GeographicCoordinates warsaw = new GeographicCoordinates(52.2296756, 21.0122287);
        assertEquals(0.0, warsaw.getDistanceTo(warsaw), 0.01);
    }

    @Test
    @DisplayName("Should reject non-geographic coordinates")
    void testGetDistanceTo_InvalidCoordinates() {
        GeographicCoordinates warsaw = new GeographicCoordinates(52.2296756, 21.0122287);
        Coordinates invalidPoint = mock(Coordinates.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> warsaw.getDistanceTo(invalidPoint));
        assertEquals("Cannot calculate distance to non-GeographicCoordinates",
                exception.getMessage());
    }
}
