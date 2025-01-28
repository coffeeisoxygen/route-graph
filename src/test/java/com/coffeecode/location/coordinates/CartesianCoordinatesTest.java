package com.coffeecode.location.coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;

@DisplayName("Cartesian Coordinates Tests")
class CartesianCoordinatesTest {
    private static final double DELTA = 0.0001;

    @Test
    @DisplayName("Should create valid coordinates")
    void shouldCreateValidCoordinates() {
        CartesianCoordinates coord = CartesianCoordinates.of(1000, 2000, 100);
        assertEquals(1000, coord.getX());
        assertEquals(2000, coord.getY());
        assertEquals(100, coord.getZ());
    }

    @Test
    @DisplayName("Should reject invalid coordinates")
    void shouldRejectInvalidCoordinates() {
        assertThrows(IllegalArgumentException.class,
            () -> CartesianCoordinates.of(Double.POSITIVE_INFINITY, 0, 0));
        assertThrows(IllegalArgumentException.class,
            () -> CartesianCoordinates.of(0, Double.NaN, 0));
    }

    @Test
    @DisplayName("Should calculate 3D distance correctly")
    void shouldCalculate3DDistance() {
        // Given
        CartesianCoordinates p1 = CartesianCoordinates.of(0, 0, 0);
        CartesianCoordinates p2 = CartesianCoordinates.of(3000, 4000, 0);
        CartesianCoordinates p3 = CartesianCoordinates.of(3000, 4000, 1000);

        // When & Then
        assertEquals(5.0, p1.getDistanceTo(p2), DELTA); // horizontal
        assertEquals(5.099, p1.getDistanceTo(p3), 0.001); // with elevation
    }

    @Test
    @DisplayName("Should handle elevation changes")
    void shouldHandleElevation() {
        CartesianCoordinates coord = CartesianCoordinates.of(1000, 2000, 100);
        CartesianCoordinates elevated = (CartesianCoordinates) coord.withElevation(200);

        assertEquals(200, elevated.getElevation());
        assertEquals(coord.getX(), elevated.getX());
        assertEquals(coord.getY(), elevated.getY());
    }
}
