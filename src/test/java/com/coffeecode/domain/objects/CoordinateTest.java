package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Coordinate Tests")
class CoordinateTest {

    @Test
    @DisplayName("Should create valid coordinate")
    void shouldCreateValidCoordinate() {
        Coordinate coordinate = Coordinate.of(40.7128, -74.0060);

        assertEquals(40.7128, coordinate.getLatitude());
        assertEquals(-74.0060, coordinate.getLongitude());
    }

    @ParameterizedTest
    @CsvSource({
        "-91.0, 0.0",
        "91.0, 0.0"
    })
    @DisplayName("Should throw exception for invalid latitude")
    void shouldThrowExceptionForInvalidLatitude(double latitude, double longitude) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Coordinate.of(latitude, longitude)
        );
        assertEquals("Latitude must be between -90 and 90 degrees!", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, -181.0",
        "0.0, 181.0"
    })
    @DisplayName("Should throw exception for invalid longitude")
    void shouldThrowExceptionForInvalidLongitude(double latitude, double longitude) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Coordinate.of(latitude, longitude)
        );
        assertEquals("Longitude must be between -180 and 180 degrees!", exception.getMessage());
    }

    @Test
    @DisplayName("Should properly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        Coordinate coord1 = Coordinate.of(40.7128, -74.0060);
        Coordinate coord2 = Coordinate.of(40.7128, -74.0060);
        Coordinate coord3 = Coordinate.of(40.7129, -74.0060);

        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
        assertEquals(coord1.hashCode(), coord2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString properly")
    void shouldImplementToString() {
        Coordinate coordinate = Coordinate.of(40.7128, -74.0060);
        String expected = "Coordinate(latitude=40.7128, longitude=-74.006)";
        assertEquals(expected, coordinate.toString());
    }
}
