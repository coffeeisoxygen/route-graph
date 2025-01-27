package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entities.base.BaseNodeTest;
import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.domain.values.location.Elevation;

@DisplayName("Network Node Tests")
class NetworkNodeTest extends BaseNodeTest {

    @Override
    protected NetworkNode.AbstractNodeBuilder<?> createBuilder() {
        return TestNode.builder();
    }

    @Nested
    @DisplayName("Creation Tests")
    class CreationTests {
        @Test
        @DisplayName("Should create with valid data")
        void shouldCreateWithValidData() {
            NetworkNode node = createDefaultNode();
            assertNotNull(node);
            assertValidNode(node);
        }

        @Test
        @DisplayName("Should create unique IDs")
        void shouldCreateUniqueIds() {
            assertUniqueIds(5);
        }
    }

    @Nested
    @DisplayName("Immutability Tests")
    class ImmutabilityTests {
        @Test
        @DisplayName("Should ensure location immutability")
        void shouldEnsureLocationImmutability() {
            // Setup
            Coordinate originalLocation = Coordinate.of(10.0, 20.0);
            NetworkNode node = createBuilder()
                .location(originalLocation)
                .type(DEFAULT_TYPE)
                .elevation(DEFAULT_ELEVATION)
                .build();

            // Verify
            Coordinate nodeLocation = node.getLocation();
            assertNotSame(originalLocation, nodeLocation);
            assertEquals(originalLocation.getLatitude(), nodeLocation.getLatitude());
            assertEquals(originalLocation.getLongitude(), nodeLocation.getLongitude());
        }

        @Test
        @DisplayName("Should ensure elevation immutability")
        void shouldEnsureElevationImmutability() {
            // Setup
            Elevation originalElevation = Elevation.of(150.0);
            NetworkNode node = createBuilder()
                .location(DEFAULT_LOCATION)
                .type(DEFAULT_TYPE)
                .elevation(originalElevation)
                .build();

            // Verify
            Elevation nodeElevation = node.getElevation();
            assertNotSame(originalElevation, nodeElevation, "Elevation should be defensively copied");
            assertEquals(originalElevation.getValue(), nodeElevation.getValue());
        }
    }
}
