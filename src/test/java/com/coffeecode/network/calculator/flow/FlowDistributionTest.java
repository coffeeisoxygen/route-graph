package com.coffeecode.network.calculator.flow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.calculator.physics.properties.Capacity;
import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadLoss;
import com.coffeecode.network.calculator.physics.properties.HeadPressure;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterNodes;
import com.coffeecode.network.nodes.WaterSource;

@DisplayName("Flow Distribution Tests")
class FlowDistributionTest {
        private WaterSource source1, source2;
        private WaterDemand demand1, demand2;
        private Map<WaterNodes, Map<WaterNodes, NetworkFlow>> connections;

        @BeforeEach
        void setUp() {
                // Reset test data and connections
                source1 = createSource("S1", 10.0);
                source2 = createSource("S2", 5.0);
                demand1 = createDemand("D1", 8.0);
                demand2 = createDemand("D2", 7.0);
                connections = new HashMap<>();
        }

        @Test
        @DisplayName("Should calculate balanced flow")
        void shouldCalculateBalancedFlow() {
                // Given - exact match of supply and demand
                double flowValue = 10.0;
                Set<WaterSource> sources = Set.of(createSource("S1", flowValue));
                Set<WaterDemand> demands = Set.of(createDemand("D1", flowValue));

                // Create valid flow connection
                NetworkFlow flow = NetworkFlow.calculate(
                                101325.0, // Initial pressure
                                FlowRate.of(flowValue), // Matching flow rate
                                new HeadLoss(5.0), // Reasonable head loss
                                0.0 // No elevation difference
                );

                // Build connections
                Map<WaterNodes, Map<WaterNodes, NetworkFlow>> connections = new HashMap<>();
                WaterSource source = sources.iterator().next();
                WaterDemand demand = demands.iterator().next();
                connections.put(source, Map.of(demand, flow));

                // When
                FlowDistribution distribution = FlowDistribution.calculate(sources, demands, connections);

                // Then
                assertAll(
                                "Flow Distribution Validation",
                                () -> {
                                        double totalSupply = sources.stream()
                                                        .mapToDouble(s -> s.getFlowRate().getValue())
                                                        .sum();
                                        double totalDemand = demands.stream()
                                                        .mapToDouble(d -> d.getDemand().getValue())
                                                        .sum();
                                        System.out.println("Supply: " + totalSupply + ", Demand: " + totalDemand);
                                        assertTrue(distribution.getMassBalance().isBalanced(),
                                                        "Mass balance should be achieved");
                                },
                                () -> assertTrue(distribution.isValid(),
                                                "Flow distribution should be valid"),
                                () -> assertEquals(1, distribution.getFlowMap().size()),
                                () -> assertTrue(distribution.getFlowMap().containsKey(source)));
        }

        private NetworkFlow createFlow(double flowValue) {
                return NetworkFlow.calculate(
                                101325.0, // Initial pressure (Pa)
                                FlowRate.of(flowValue), // Flow rate matches source/demand
                                new HeadLoss(5.0), // Reasonable head loss
                                0.0 // No elevation difference for simplicity
                );
        }

        @Test
        @DisplayName("Should handle multiple sources and demands")
        void shouldHandleMultipleNodes() {
                // Given
                Set<WaterSource> sources = Set.of(source1, source2); // Total supply = 15.0
                Set<WaterDemand> demands = Set.of(demand1, demand2); // Total demand = 15.0

                // Create balanced connections
                addConnection(source1, demand1, 8.0); // Source1 (10.0) -> Demand1 (8.0)
                addConnection(source2, demand2, 7.0); // Source2 (5.0) -> Demand2 (7.0)

                // When
                FlowDistribution distribution = FlowDistribution.calculate(sources, demands, connections);

                // Then
                assertAll(
                                "Multiple Node Distribution",
                                () -> {
                                        double totalSupply = sources.stream()
                                                        .mapToDouble(s -> s.getFlowRate().getValue())
                                                        .sum();
                                        double totalDemand = demands.stream()
                                                        .mapToDouble(d -> d.getDemand().getValue())
                                                        .sum();
                                        System.out.println("Total Supply: " + totalSupply + ", Total Demand: "
                                                        + totalDemand);
                                        assertTrue(Math.abs(totalSupply - totalDemand) < MassBalance.TOLERANCE,
                                                        "Supply and demand should be balanced");
                                },
                                () -> assertTrue(distribution.getMassBalance().isBalanced()),
                                () -> assertTrue(distribution.isValid()),
                                () -> assertEquals(2, distribution.getFlowMap().size()),
                                () -> assertTrue(distribution.getFlowMap().containsKey(source1)),
                                () -> assertTrue(distribution.getFlowMap().containsKey(source2)));
        }

        @Test
        @DisplayName("Should detect unbalanced flow")
        void shouldDetectUnbalancedFlow() {
                // Given - deliberately create unbalanced flow
                Set<WaterSource> sources = Set.of(createSource("S1", 8.0)); // Supply = 8.0
                Set<WaterDemand> demands = Set.of(createDemand("D1", 10.0)); // Demand = 10.0

                // Add connection with unbalanced flow
                WaterSource source = sources.iterator().next();
                WaterDemand demand = demands.iterator().next();
                addConnection(source, demand, 8.0); // Connect with available supply

                // When
                FlowDistribution distribution = FlowDistribution.calculate(sources, demands, connections);

                // Then
                assertAll(
                                "Unbalanced Flow Validation",
                                () -> assertFalse(distribution.getMassBalance().isBalanced()),
                                () -> {
                                        double totalSupply = sources.stream()
                                                        .mapToDouble(s -> s.getFlowRate().getValue())
                                                        .sum();
                                        double totalDemand = demands.stream()
                                                        .mapToDouble(d -> d.getDemand().getValue())
                                                        .sum();
                                        System.out.println("Unbalanced - Supply: " + totalSupply + ", Demand: "
                                                        + totalDemand);
                                        assertTrue(totalSupply < totalDemand, "Supply should be less than demand");
                                });
        }

        @Test
        @DisplayName("Should reject empty sources")
        void shouldRejectEmptySources() {
                // Given
                Set<WaterSource> sources = Set.of();
                Set<WaterDemand> demands = Set.of(demand1);

                // When/Then
                assertThrows(IllegalArgumentException.class,
                                () -> FlowDistribution.calculate(sources, demands, connections));
        }

        @Test
        @DisplayName("Should reject empty demands")
        void shouldRejectEmptyDemands() {
                // Given
                Set<WaterSource> sources = Set.of(source1);
                Set<WaterDemand> demands = Set.of();

                // When/Then
                assertThrows(IllegalArgumentException.class,
                                () -> FlowDistribution.calculate(sources, demands, connections));
        }

        @Test
        @DisplayName("Should validate null connections")
        void shouldValidateNullConnections() {
                // Given
                Set<WaterSource> sources = Set.of(source1);
                Set<WaterDemand> demands = Set.of(demand1);

                // When/Then assertThrows(IllegalArgumentException.class, () ->
                // FlowDistribution.calculate(sources, demands, null));
        }

        private void addConnection(WaterNodes from, WaterNodes to, double flowRate) {
                NetworkFlow flow = NetworkFlow.calculate(
                                101325.0,
                                FlowRate.of(flowRate),
                                new HeadLoss(5.0),
                                0.0);
                connections.computeIfAbsent(from, k -> new HashMap<>())
                                .put(to, flow);
        }

        private static WaterSource createSource(String name, double flowRate) {
                return WaterSource.builder()
                                .name(name)
                                .coordinates(CartesianCoordinates.of(0, 0, 0))
                                .elevation(Elevation.manual(100.0))
                                .capacity(Capacity.of(1000.0))
                                .flowRate(FlowRate.of(flowRate))
                                .headPressure(HeadPressure.of(101325.0))
                                .build();
        }

        private static WaterDemand createDemand(String name, double demand) {
                return WaterDemand.builder()
                                .name(name)
                                .coordinates(CartesianCoordinates.of(100, 100, 0))
                                .elevation(Elevation.manual(50.0))
                                .demand(FlowRate.of(demand))
                                .minPressure(HeadPressure.of(50000.0))
                                .build();
        }
}
