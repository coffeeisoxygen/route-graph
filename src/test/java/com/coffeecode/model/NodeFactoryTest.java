package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.coffeecode.model.coordinates.GeographicCoordinates;

class NodeFactoryTest {
    @Test
    void shouldCreateGeographicNode() {
        // Given
        double latitude = -6.2088;
        double longitude = 106.8456;

        // When
        Node node = NodeFactory.createGeographicNode(
                latitude, longitude, 8.0, 1.5, 101325.0);

        // Then
        assertTrue(node.getCoordinates() instanceof GeographicCoordinates);
        GeographicCoordinates coords = (GeographicCoordinates) node.getCoordinates();
        assertEquals(latitude, coords.getLatitude());
        assertEquals(longitude, coords.getLongitude());
    }
}
