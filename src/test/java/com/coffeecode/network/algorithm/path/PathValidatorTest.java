package com.coffeecode.network.algorithm.path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.calculator.physics.properties.Capacity;
import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadPressure;
import com.coffeecode.network.core.WaterNetwork;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterSource;

@DisplayName("PathValidator Tests")
class PathValidatorTest {

    private PathValidator validator;
    private WaterNetwork network;
    private WaterSource validSource;
    private WaterDemand validDemand;

    @BeforeEach
    void setUp() {
        validator = PathValidator.createInstance();
        validSource = WaterSource.builder()
            .name("Source-1")
            .coordinates(CartesianCoordinates.of(0, 0, 0))
            .elevation(Elevation.manual(100.0))

            .capacity(Capacity.of(1000.0))
            .flowRate(FlowRate.of(10.0))

            .headPressure(HeadPressure.of(101325.0))
            .build();

        validDemand = WaterDemand.builder()
            .name("Demand-1")
            .coordinates(CartesianCoordinates.of(100, 100, 0))
            .elevation(Elevation.manual(50.0))
            .demand(FlowRate.of(5.0))
            .minPressure(HeadPressure.of(50000.0))
            .build();

        network = WaterNetwork.builder()
            .addSource(validSource)
            .addDemand(validDemand)
            .build();
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {
        @Test
        @DisplayName("Should accept valid source and demand")
        void shouldAcceptValidNodes() {
            assertDoesNotThrow(() ->
                validator.validateNodes(network, validSource, validDemand)
            );
        }

        @Test
        @DisplayName("Should reject invalid source")
        void shouldRejectInvalidSource() {
            WaterSource invalidSource = WaterSource.builder()
                .name("Invalid")
                .coordinates(CartesianCoordinates.of(0, 0, 0))
                .elevation(Elevation.manual(100.0))
                .capacity(Capacity.of(1000.0))
                .flowRate(FlowRate.of(10.0))
                .headPressure(HeadPressure.of(101325.0))
                .build();

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validator.validateNodes(network, invalidSource, validDemand)
            );
            assertEquals("Source node not in network", exception.getMessage());
        }

        @Test
        @DisplayName("Should reject invalid demand")
        void shouldRejectInvalidDemand() {
            WaterDemand invalidDemand = WaterDemand.builder()
                .name("Invalid")
                .coordinates(CartesianCoordinates.of(200, 200, 0))
                .elevation(Elevation.manual(75.0))
                .demand(FlowRate.of(5.0))
                .minPressure(HeadPressure.of(50000.0))
                .build();

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                validator.validateNodes(network, validSource, invalidDemand)
            );
            assertEquals("Target node not in network", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Should handle empty network")
    void shouldHandleEmptyNetwork() {
        WaterNetwork emptyNetwork = WaterNetwork.builder().build();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            validator.validateNodes(emptyNetwork, validSource, validDemand)
        );
        assertEquals("Source node not in network", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle null inputs")
    void shouldHandleNullInputs() {
        assertAll(
            () -> assertThrows(NullPointerException.class, () ->
                validator.validateNodes(null, validSource, validDemand)),
            () -> assertThrows(NullPointerException.class, () ->
                validator.validateNodes(network, null, validDemand)),
            () -> assertThrows(NullPointerException.class, () ->
                validator.validateNodes(network, validSource, null))
        );
    }
}
