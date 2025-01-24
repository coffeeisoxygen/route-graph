package com.coffeecode.core.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.core.constants.TestConstants;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RouteTest {

    private Location source;
    private Location destination;
    private Route.RouteType type;
    private double distance;

    @BeforeEach
    void setUp() {
        // Jakarta coordinates
        source = Location.createNew("Jakarta", TestConstants.JAKARTA_LONG, TestConstants.JAKARTA_LAT);
        // Bandung coordinates
        destination = Location.createNew("Bandung", TestConstants.BANDUNG_LONG, TestConstants.BANDUNG_LAT);
        type = Route.RouteType.BIDIRECTIONAL;
        distance = TestConstants.JKT_BDG_ACTUAL; // Approximate distance Jakarta-Bandung in km
    }

    @Test
    void testCreateRoute_ValidData() {
        // Act
        Route route = Route.createRoute(source, destination, type);

        // Assert
        assertNotNull(route);
        assertNotNull(route.getIdRoute());
        assertEquals(source, route.getSource());
        assertEquals(destination, route.getDestination());
        assertEquals(type, route.getType());
        assertEquals(distance, route.getDistance(), 1.0); // Delta 1.0 km for floating point comparison
    }

    @Test
    void testCreateRoute_NullSource() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Route.createRoute(null, destination, type)
        );
        assertEquals("Source and destination cannot be null", exception.getMessage());
    }

    @Test
    void testCreateRoute_NullDestination() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Route.createRoute(source, null, type)
        );
        assertEquals("Source and destination cannot be null", exception.getMessage());
    }

    @Test
    void testCreateRoute_DefaultType() {
        // Act
        Route route = Route.createRoute(source, destination, null);

        // Assert
        assertEquals(Route.RouteType.BIDIRECTIONAL, route.getType());
    }

    @Test
    void testContainsLocation() {
        // Arrange
        Route route = Route.createRoute(source, destination, type);
        Location otherLocation = Location.createNew("Singapore", 103.8198, 1.3521);

        // Assert
        assertTrue(route.containsLocation(source));
        assertTrue(route.containsLocation(destination));
        assertFalse(route.containsLocation(otherLocation));
    }

    @Test
    void testGetOppositeLocation() {
        // Arrange
        Route route = Route.createRoute(source, destination, type);
        Location otherLocation = Location.createNew("Singapore", 103.8198, 1.3521);

        // Assert
        assertEquals(destination, route.getOppositeLocation(source));
        assertEquals(source, route.getOppositeLocation(destination));
        assertThrows(IllegalArgumentException.class,
                () -> route.getOppositeLocation(otherLocation),
                "Location is not part of this route"
        );
    }
}
