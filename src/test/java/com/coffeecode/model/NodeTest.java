package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.coordinates.CartesianCoordinates;
import com.coffeecode.model.coordinates.DistanceUnit;

class NodeTest {

    @Test
    @DisplayName("Should create node with cartesian coordinates")
    void shouldCreateNodeWithCartesianCoordinates() {
        // Given
        CartesianCoordinates coords = new CartesianCoordinates(0, 0);

        // When
        Node node = Node.builder()
                .coordinates(coords)
                .elevation(100)
                .velocity(1.5)
                .pressure(101325)
                .build();

        // Then
        assertEquals(coords, node.getCoordinates());
        assertEquals(100, node.getElevation());
        assertEquals(1.5, node.getVelocity());
        assertEquals(101325, node.getPressure());
    }

    @Test
    @DisplayName("Should calculate distance between nodes")
    void shouldCalculateDistance() {
        // Given
        Node node1 = Node.builder()
                .coordinates(new CartesianCoordinates(0, 0))
                .elevation(100)
                .velocity(1.5)
                .pressure(101325)
                .build();

        Node node2 = Node.builder()
                .coordinates(new CartesianCoordinates(3, 4))
                .elevation(100)
                .velocity(1.5)
                .pressure(101325)
                .build();

        // When
        double distance = node1.getDistanceTo(node2);

        // Then
        assertEquals(0.005, distance, 0.001); // 5m = 0.005km
    }

    @Test
    @DisplayName("Should calculate distance with unit conversion")
    void shouldCalculateDistanceWithUnit() {
        // Given
        Node node1 = Node.builder()
                .coordinates(new CartesianCoordinates(0, 0))
                .build();
        Node node2 = Node.builder()
                .coordinates(new CartesianCoordinates(3, 4))
                .build();

        // When
        double distanceInMeters = node1.getDistanceTo(node2, DistanceUnit.METERS);

        // Then
        assertEquals(5.0, distanceInMeters, 0.001);
    }
}
