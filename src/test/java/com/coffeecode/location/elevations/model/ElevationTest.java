package com.coffeecode.location.elevations.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Elevation Tests")
class ElevationTest {

    @Test
    @DisplayName("Should create elevation from API")
    void shouldCreateFromApi() {
        // When
        Elevation elevation = Elevation.fromApi(100.0);

        // Then
        assertEquals(100.0, elevation.getMeters());
        assertEquals(Elevation.Type.API, elevation.getType());
    }

    @Test
    @DisplayName("Should create default elevation")
    void shouldCreateDefault() {
        // When
        Elevation elevation = Elevation.defaultValue();

        // Then
        assertEquals(0.0, elevation.getMeters());
        assertEquals(Elevation.Type.DEFAULT, elevation.getType());
    }

    @ParameterizedTest
    @ValueSource(doubles = { -501.0, 9001.0 })
    @DisplayName("Should reject invalid elevations")
    void shouldRejectInvalidElevations(double meters) {
        // Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Elevation.manual(meters));
        assertEquals("Elevation must be between -500.0 and 9000.0 meters", exception.getMessage());
    }

    @Test
    @DisplayName("Should create manual elevation within bounds")
    void shouldCreateManualElevation() {
        double validElevation = 100.0;
        Elevation elevation = Elevation.manual(validElevation);
        assertEquals(validElevation, elevation.getMeters());
        assertEquals(Elevation.Type.MANUAL, elevation.getType());
    }

    @Test
    @DisplayName("Should handle zero elevation")
    void shouldHandleZeroElevation() {
        Elevation elevation = Elevation.manual(0.0);
        assertEquals(0.0, elevation.getMeters());
    }
}
