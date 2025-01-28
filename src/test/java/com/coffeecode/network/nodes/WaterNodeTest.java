package com.coffeecode.network.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.calculator.physics.properties.Capacity;
import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadPressure;
import com.coffeecode.network.nodes.WaterNodes.NodeType;

@DisplayName("Water Node Tests")
class WaterNodeTest {

    @Nested
    @DisplayName("WaterSource Tests")
    class WaterSourceTests {
        private static final CartesianCoordinates VALID_COORDINATES = CartesianCoordinates.of(0, 0, 0);
        private static final Elevation VALID_ELEVATION = Elevation.manual(100.0);
        private static final Capacity VALID_CAPACITY = Capacity.of(1000.0);
        private static final FlowRate VALID_FLOW = FlowRate.of(10.0);
        private static final HeadPressure VALID_PRESSURE = HeadPressure.of(101325.0);

        @Test
        @DisplayName("Should create valid water source")
        void shouldCreateValidWaterSource() {
            // Given
            var coordinates = CartesianCoordinates.of(0, 0, 0);
            var elevation = Elevation.manual(100.0);
            var capacity = Capacity.of(1000.0);
            var flowRate = FlowRate.of(10.0);
            var headPressure = HeadPressure.of(101325.0);

            // When
            WaterSource source = WaterSource.builder()
                    .name("Source-1")
                    .coordinates(coordinates)
                    .elevation(elevation)
                    .capacity(capacity)
                    .flowRate(flowRate)
                    .headPressure(headPressure)
                    .build();

            // Then
            assertNotNull(source.getId());
            assertEquals("Source-1", source.getName());
            assertEquals(coordinates, source.getCoordinates());
            assertEquals(elevation, source.getElevation());
            assertEquals(NodeType.SOURCE, source.getType());
            assertEquals(capacity, source.getCapacity());
            assertEquals(flowRate, source.getFlowRate());
            assertEquals(headPressure, source.getHeadPressure());
        }

        @Test
        @DisplayName("Should reject null name")
        void shouldRejectNullName() {
            assertThrows(IllegalArgumentException.class, () -> WaterSource.builder()
                    .coordinates(VALID_COORDINATES)
                    .elevation(VALID_ELEVATION)
                    .capacity(VALID_CAPACITY)
                    .flowRate(VALID_FLOW)
                    .headPressure(VALID_PRESSURE)
                    .build());
        }

        @Test
        @DisplayName("Should reject null coordinates")
        void shouldRejectNullCoordinates() {
            assertThrows(IllegalArgumentException.class, () -> WaterSource.builder()
                    .name("Source-1")
                    .elevation(VALID_ELEVATION)
                    .capacity(VALID_CAPACITY)
                    .flowRate(VALID_FLOW)
                    .headPressure(VALID_PRESSURE)
                    .build());
        }

        @Test
        @DisplayName("Should reject null values")
        void shouldRejectNullValues() {
            // Given
            var builder = WaterSource.builder();

            // Then
            assertThrows(IllegalArgumentException.class, () -> builder.build());
        }
    }

    @Nested
    @DisplayName("WaterDemand Tests")
    class WaterDemandTests {
        private static final CartesianCoordinates VALID_COORDINATES = CartesianCoordinates.of(100, 100, 0);
        private static final Elevation VALID_ELEVATION = Elevation.manual(50.0);
        private static final FlowRate VALID_DEMAND = FlowRate.of(5.0);
        private static final HeadPressure VALID_MIN_PRESSURE = HeadPressure.of(50000.0);

        @Test
        @DisplayName("Should create valid water demand")
        void shouldCreateValidWaterDemand() {
            // When
            WaterDemand demand = WaterDemand.builder()
                    .name("Demand-1")
                    .coordinates(VALID_COORDINATES)
                    .elevation(VALID_ELEVATION)
                    .demand(VALID_DEMAND)
                    .minPressure(VALID_MIN_PRESSURE)
                    .build();

            // Then
            assertNotNull(demand);
            assertEquals("Demand-1", demand.getName());
            assertEquals(VALID_COORDINATES, demand.getCoordinates());
            assertEquals(VALID_ELEVATION, demand.getElevation());
            assertEquals(NodeType.DEMAND, demand.getType());
        }

        @Test
        @DisplayName("Should reject null demand")
        void shouldRejectNullDemand() {
            assertThrows(IllegalArgumentException.class, () -> WaterDemand.builder()
                    .name("Demand-1")
                    .coordinates(VALID_COORDINATES)
                    .elevation(VALID_ELEVATION)
                    .minPressure(VALID_MIN_PRESSURE)
                    .build());
        }
    }
}
