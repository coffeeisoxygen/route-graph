package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeoNodeTest {

    @Test
    @DisplayName("Should create GeoNode with valid parameters")
    void shouldCreateGeoNode() {
        // Given
        double lat = -6.2088; // Jakarta latitude
        double lon = 106.8456; // Jakarta longitude
        double elev = 8.0; // Jakarta average elevation
        double vel = 1.5;
        double press = 101325.0;

        // When
        GeoNode node = new GeoNode(lat, lon, elev, vel, press);

        // Then
        assertEquals(lat, node.getLatitude());
        assertEquals(lon, node.getLongitude());
        assertEquals(elev, node.getElevation());
    }

    @ParameterizedTest
    @CsvSource({
            "-6.2088,106.8456,-6.1751,106.8272,8000", // Jakarta - Tangerang ~8km
            "51.5074,-0.1278,48.8566,2.3522,342000" // London - Paris ~342km
    })
    @DisplayName("Should calculate geographic distances correctly")
    void shouldCalculateGeoDistance(double lat1, double lon1,
            double lat2, double lon2,
            double expectedDist) {
        // Given
        GeoNode node1 = new GeoNode(lat1, lon1, 0, 1.5, 101325);
        GeoNode node2 = new GeoNode(lat2, lon2, 0, 1.5, 101325);

        // When
        double distance = node1.getDistanceTo(node2);

        // Then
        assertEquals(expectedDist, distance, expectedDist * 0.01); // 1% tolerance
    }

    @Test
    @DisplayName("Should handle mixed node types correctly")
    void shouldHandleMixedNodes() {
        // Given
        GeoNode geoNode = new GeoNode(0, 0, 10, 1.5, 101325);
        Node plainNode = new Node(100, 100, 10, 1.5, 101325);

        // When
        double distance = geoNode.getDistanceTo(plainNode);

        // Then
        assertEquals(141.42, distance, 0.01); // Should use Euclidean
    }
}
