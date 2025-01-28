package com.coffeecode.network.core;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.calculator.physics.properties.Capacity;
import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadPressure;
import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.edges.PipeMaterial;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterSource;

@DisplayName("WaterNetwork Tests")
class WaterNetworkTest {

        @Nested
        @DisplayName("Network Construction Tests")
        class NetworkConstructionTests {
                private static final WaterSource SOURCE = WaterSource.builder()
                                .name("Source-1")
                                .coordinates(CartesianCoordinates.of(0, 0, 0))
                                .elevation(Elevation.manual(100.0))
                                .capacity(Capacity.of(1000.0))
                                .flowRate(FlowRate.of(10.0))
                                .headPressure(HeadPressure.of(101325.0))
                                .build();

                private static final WaterDemand DEMAND = WaterDemand.builder()
                                .name("Demand-1")
                                .coordinates(CartesianCoordinates.of(100, 100, 0))
                                .elevation(Elevation.manual(50.0))
                                .demand(FlowRate.of(5.0))
                                .minPressure(HeadPressure.of(50000.0))
                                .build();

                private static final Pipe PIPE = new Pipe(0.1, 10.0, PipeMaterial.PVC);

                @Test
                @DisplayName("Should create valid network")
                void shouldCreateValidNetwork() {
                        // When
                        WaterNetwork network = WaterNetwork.builder()
                                        .addSource(SOURCE)
                                        .addDemand(DEMAND)
                                        .connectNodes(SOURCE, DEMAND, PIPE)
                                        .build();

                        // Then
                        assertAll(
                                        () -> assertEquals(1, network.getSources().size()),
                                        () -> assertEquals(1, network.getDemands().size()),
                                        () -> assertEquals(1, network.getPipes().size()),
                                        () -> assertTrue(network.getAdjacencyMap().containsKey(SOURCE)),
                                        () -> assertTrue(network.getAdjacencyMap().get(SOURCE).containsKey(DEMAND)));
                }

                @Test
                @DisplayName("Should reject network without sources")
                void shouldRejectNetworkWithoutSources() {
                        // When/Then
                        assertThrows(IllegalStateException.class, () -> WaterNetwork.builder()
                                        .addDemand(DEMAND)
                                        .build());
                }

                @Test
                @DisplayName("Should reject network without demands")
                void shouldRejectNetworkWithoutDemands() {
                        // When/Then
                        assertThrows(IllegalStateException.class, () -> WaterNetwork.builder()
                                        .addSource(SOURCE)
                                        .build());
                }
        }

        @Nested
        @DisplayName("Network Connectivity Tests")
        class NetworkConnectivityTests {
                @Test
                @DisplayName("Should reject disconnected demand nodes")
                void shouldRejectDisconnectedDemands() {
                        // Given
                        WaterSource source = WaterSource.builder()
                                        .name("Source-1")
                                        .coordinates(CartesianCoordinates.of(0, 0, 0))
                                        .elevation(Elevation.manual(100.0))
                                        .capacity(Capacity.of(1000.0))
                                        .flowRate(FlowRate.of(10.0))
                                        .headPressure(HeadPressure.of(101325.0))
                                        .build();

                        WaterDemand disconnectedDemand = WaterDemand.builder()
                                        .name("Demand-1")
                                        .coordinates(CartesianCoordinates.of(100, 100, 0))
                                        .elevation(Elevation.manual(50.0))
                                        .demand(FlowRate.of(5.0))
                                        .minPressure(HeadPressure.of(50000.0))
                                        .build();

                        // When/Then
                        assertThrows(IllegalStateException.class, () -> WaterNetwork.builder()
                                        .addSource(source)
                                        .addDemand(disconnectedDemand)
                                        .build());
                }

                @Test
                @DisplayName("Should reject duplicate connections")
                void shouldRejectDuplicateConnections() {
                        // Given
                        WaterSource source = WaterSource.builder()
                                        .name("Source-1")
                                        .coordinates(CartesianCoordinates.of(0, 0, 0))
                                        .elevation(Elevation.manual(100.0))
                                        .capacity(Capacity.of(1000.0))
                                        .flowRate(FlowRate.of(10.0))
                                        .headPressure(HeadPressure.of(101325.0))
                                        .build();

                        WaterDemand demand = WaterDemand.builder()
                                        .name("Demand-1")
                                        .coordinates(CartesianCoordinates.of(100, 100, 0))
                                        .elevation(Elevation.manual(50.0))
                                        .demand(FlowRate.of(5.0))
                                        .minPressure(HeadPressure.of(50000.0))
                                        .build();

                        Pipe pipe1 = new Pipe(0.1, 10.0, PipeMaterial.PVC);
                        Pipe pipe2 = new Pipe(0.1, 10.0, PipeMaterial.PVC);

                        // When/Then
                        WaterNetwork.Builder builder = WaterNetwork.builder()
                                        .addSource(source)
                                        .addDemand(demand)
                                        .connectNodes(source, demand, pipe1);

                        assertThrows(IllegalArgumentException.class, () -> builder.connectNodes(source, demand, pipe2));
                }
        }
}
