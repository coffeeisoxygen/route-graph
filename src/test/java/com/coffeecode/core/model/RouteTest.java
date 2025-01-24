package com.coffeecode.core.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.coffeecode.core.constants.TestConstants;

@DisplayName("Route Entity Tests")
class RouteTest {

    private Location jakarta;
    private Location bandung;
    private Route.RouteType type;

    @BeforeEach
    void setUp() {
        jakarta = Location.createNew("Jakarta", TestConstants.JAKARTA_LONG, TestConstants.JAKARTA_LAT);
        bandung = Location.createNew("Bandung", TestConstants.BANDUNG_LONG, TestConstants.BANDUNG_LAT);
        type = Route.RouteType.BIDIRECTIONAL;
    }

    @Nested
    @DisplayName("Route Creation Tests")
    class RouteCreationTests {

        @Test
        @DisplayName("Should create route with valid data")
        void testCreateRoute_ValidData() {
            Route route = Route.createRoute(jakarta, bandung, type);

            assertAll(
                    () -> assertNotNull(route.getIdRoute()),
                    () -> assertEquals(jakarta, route.getSource()),
                    () -> assertEquals(bandung, route.getDestination()),
                    () -> assertEquals(type, route.getType()),
                    () -> assertEquals(TestConstants.JKT_BDG_ACTUAL, route.getDistance(), 1.0)
            );
        }

        @Test
        @DisplayName("Should calculate correct distance")
        void testCreateRoute_DistanceCalculation() {
            Route route = Route.createRoute(jakarta, bandung, type);
            assertTrue(route.getDistance() > 0);
            assertTrue(route.getDistance() < 200); // Jakarta-Bandung should be less than 200km
        }
    }

    @Nested
    @DisplayName("Route Validation Tests")
    class RouteValidationTests {

        @Test
        @DisplayName("Should reject null locations")
        void testCreateRoute_NullLocations() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> Route.createRoute(null, bandung, type)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> Route.createRoute(jakarta, null, type))
            );
        }

        @Test
        @DisplayName("Should handle same location route")
        void testCreateRoute_SameLocation() {
            Route route = Route.createRoute(jakarta, jakarta, type);
            assertEquals(0.0, route.getDistance(), 0.0001);
        }
    }

    @Nested
    @DisplayName("Route Type Tests")
    class RouteTypeTests {

        @Test
        @DisplayName("Should default to bidirectional")
        void testCreateRoute_DefaultType() {
            Route route = Route.createRoute(jakarta, bandung, null);
            assertEquals(Route.RouteType.BIDIRECTIONAL, route.getType());
        }

        @Test
        @DisplayName("Should handle one-way routes")
        void testCreateRoute_OneWay() {
            Route route = Route.createRoute(jakarta, bandung, Route.RouteType.ONE_WAY);
            assertEquals(Route.RouteType.ONE_WAY, route.getType());
        }
    }

    @Nested
    @DisplayName("Route Operation Tests")
    class RouteOperationTests {

        @Test
        @DisplayName("Should check location containment")
        void testContainsLocation() {
            Route route = Route.createRoute(jakarta, bandung, type);
            Location other = Location.createNew("Other", 0.0, 0.0);

            assertAll(
                    () -> assertTrue(route.containsLocation(jakarta)),
                    () -> assertTrue(route.containsLocation(bandung)),
                    () -> assertFalse(route.containsLocation(other))
            );
        }

        @Test
        @DisplayName("Should get opposite location")
        void testGetOppositeLocation() {
            Route route = Route.createRoute(jakarta, bandung, type);

            assertAll(
                    () -> assertEquals(bandung, route.getOppositeLocation(jakarta)),
                    () -> assertEquals(jakarta, route.getOppositeLocation(bandung)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> route.getOppositeLocation(Location.createNew("Other", 0.0, 0.0)))
            );
        }
    }
}
