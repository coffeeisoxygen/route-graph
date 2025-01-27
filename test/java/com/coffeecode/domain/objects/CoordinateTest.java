package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.validation.exceptions.ValidationException;

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
    @DisplayName("Should throw ValidationException for invalid latitude")
    void shouldThrowValidationExceptionForInvalidLatitude(double latitude, double longitude) {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Coordinate.of(latitude, longitude)
        );
        assertTrue(exception.getMessage().contains("Latitude must be between -90 and 90 degrees"));
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, -181.0",
        "0.0, 181.0"
    })
    @DisplayName("Should throw ValidationException for invalid longitude")
    void shouldThrowValidationExceptionForInvalidLongitude(double latitude, double longitude) {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Coordinate.of(latitude, longitude)
        );
        assertTrue(exception.getMessage().contains("Longitude must be between -180 and 180 degrees"));
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
