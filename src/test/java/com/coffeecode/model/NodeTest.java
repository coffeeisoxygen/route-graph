package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    @DisplayName("Should create node with valid parameters")
    void shouldCreateNodeWithValidParameters() {
        // Given
        double x = 1.0;
        double y = 2.0;
        double elevation = 100.0;
        double pressure = 101325.0; // 1 atm

        // When
        Node node = new Node(x, y, elevation, pressure);

        // Then
        assertEquals(x, node.getX());
        assertEquals(y, node.getY());
        assertEquals(elevation, node.getElevation());
        assertEquals(pressure, node.getPressure());
    }
}
