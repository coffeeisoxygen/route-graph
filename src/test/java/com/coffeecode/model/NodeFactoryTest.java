package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.coordinates.CartesianCoordinates;
import com.coffeecode.model.coordinates.GeographicCoordinates;

@DisplayName("Node Factory Tests")
class NodeFactoryTest {

    @Test
    @DisplayName("Should create node with cartesian coordinates")
    void shouldCreateCartesianNode() {
        // Given
        double x = 10.0;
        double y = 20.0;
        double elevation = 100.0;
        double velocity = 1.5;
        double pressure = 101325.0;

        // When
        Node node = NodeFactory.createCartesianNode(x, y, elevation, velocity, pressure);

        // Then
        assertTrue(node.getCoordinates() instanceof CartesianCoordinates);
        CartesianCoordinates coords = (CartesianCoordinates) node.getCoordinates();
        assertEquals(x, coords.getX());
        assertEquals(y, coords.getY());
        assertEquals(elevation, node.getElevation());
    }

    @Test
    @DisplayName("Should create node with geographic coordinates")
    void shouldCreateGeographicNode() {
        // Given
        double latitude = -6.2088;
        double longitude = 106.8456;
        double elevation = 100.0;
        double velocity = 1.5;
        double pressure = 101325.0;

        // When
        Node node = NodeFactory.createGeographicNode(
                latitude, longitude, elevation, velocity, pressure);

        // Then
        assertTrue(node.getCoordinates() instanceof GeographicCoordinates);
        GeographicCoordinates coords = (GeographicCoordinates) node.getCoordinates();
        assertEquals(latitude, coords.getLatitude());
        assertEquals(longitude, coords.getLongitude());
    }

    @Test
    @DisplayName("Should validate geographic coordinates")
    void shouldValidateGeographicCoordinates() {
        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            NodeFactory.createGeographicNode(
                -91, // Invalid latitude
                106.8456,
                100.0,
                1.5,
                101325.0
            )
        );
        assertEquals("Invalid latitude", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate physical parameters")
    void shouldValidatePhysicalParameters() {
        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            NodeFactory.createCartesianNode(
                0.0,
                0.0,
                100.0,
                -1.0, // Invalid negative velocity
                101325.0
            )
        );
        assertEquals("Invalid velocity", exception.getMessage());
    }
}
