package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NodeTest {

    @Test
    @DisplayName("Should create node with valid parameters")
    void shouldCreateNodeWithValidParameters() {
        // Given
        double x = 1.0;
        double y = 2.0;
        double elevation = 100.0;
        double velocity = 1.5;
        double pressure = 101325.0;

        // When
        Node node = new Node(x, y, elevation, velocity, pressure);

        // Then
        assertEquals(x, node.getX());
        assertEquals(y, node.getY());
        assertEquals(elevation, node.getElevation());
        assertEquals(velocity, node.getVelocity());
        assertEquals(pressure, node.getPressure());
    }

    @Test
    @DisplayName("Should calculate distance correctly")
    void shouldCalculateDistanceCorrectly() {
        // Given
        Node node1 = new Node(0.0, 0.0, 100.0, 1.5, 101325.0);
        Node node2 = new Node(3.0, 4.0, 100.0, 1.5, 101325.0);

        // When
        double distance = node1.getDistanceTo(node2);

        // Then
        assertEquals(5.0, distance, 0.001);
    }
}
