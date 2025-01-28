package com.coffeecode.location.elevations.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Elevation Model Tests")
class ElevationTest {

    @Test
    @DisplayName("Should create from API source")
    void shouldCreateFromApi() {
        Elevation elevation = Elevation.fromApi(100.0);
        assertEquals(100.0, elevation.getMeters());
        assertEquals(Elevation.Type.API, elevation.getType());
    }

    @Test
    @DisplayName("Should create default value")
    void shouldCreateDefault() {
        Elevation elevation = Elevation.defaultValue();
        assertEquals(0.0, elevation.getMeters());
        assertEquals(Elevation.Type.DEFAULT, elevation.getType());
    }

    @ParameterizedTest
    @ValueSource(doubles = { -501.0, 9001.0 })
    @DisplayName("Should reject invalid elevations")
    void shouldRejectInvalidElevations(double meters) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Elevation.manual(meters));
        assertEquals("Elevation must be between -500.0 and 9000.0 meters", exception.getMessage());
    }
}
