package com.coffeecode.network.algorithm;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.calculator.physics.properties.Capacity;
import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadPressure;
import com.coffeecode.network.core.WaterNetwork;
import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.edges.PipeMaterial;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterSource;

@DisplayName("PathFinder Tests")
class PathFinderTest {
    private PathFinder pathFinder;
    private WaterNetwork network;
    private WaterSource source;
    private WaterDemand demand;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
        pathFinder = new PathFinder();

        source = createSource("Source-1", 0, 0, 100.0);
        demand = createDemand("Demand-1", 100, 100, 50.0);
        pipe = new Pipe(0.1, 10.0, PipeMaterial.PVC);

        // Create valid network with connection
        network = createNetwork(source, demand, pipe);
    }

    @Test
    @DisplayName("Should find valid path between source and demand")
    void shouldFindValidPath() {
        // When
        List<PathFinder.Path> paths = pathFinder.findPaths(network, source, demand);

        // Then
        assertAll(
                () -> assertNotNull(paths, "Paths should not be null"),
                () -> assertFalse(paths.isEmpty(), "Should find at least one path"),
                () -> assertEquals(2, paths.get(0).getNodes().size(), "Path should have source and demand"),
                () -> assertEquals(source, paths.get(0).getNodes().get(0), "Should start at source"),
                () -> assertEquals(demand, paths.get(0).getNodes().get(1), "Should end at demand"),
                () -> assertEquals(1, paths.get(0).getPipes().size(), "Should have one pipe"),
                () -> assertEquals(pipe, paths.get(0).getPipes().get(0), "Should use correct pipe"));
    }

    @Test
    @DisplayName("Should handle nodes not in network")
    void shouldHandleInvalidNodes() {
        // Given
        WaterSource invalidSource = WaterSource.builder()
                .name("Invalid")
                .coordinates(CartesianCoordinates.of(0, 0, 0))
                .elevation(Elevation.manual(100.0))
                .capacity(Capacity.of(1000.0))
                .flowRate(FlowRate.of(10.0))
                .headPressure(HeadPressure.of(101325.0))
                .build();

        // When/Then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> pathFinder.findPaths(network, invalidSource, demand));
        assertEquals("Source node not in network", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle no path between nodes")
    void shouldHandleNoPath() {
        // Given - Create network without connections
        WaterNetwork networkWithoutPath = WaterNetwork.builder()
                .addSource(source)
                .addDemand(createDemand("Demand-2", 200, 200, 75.0))
                .build();

        // When
        List<PathFinder.Path> paths = pathFinder.findPaths(
                networkWithoutPath,
                source,
                demand);

        // Then
        assertTrue(paths.isEmpty(), "Should return empty list when no path exists");
    }

    private WaterNetwork createNetwork(WaterSource source, WaterDemand demand, Pipe pipe) {
        return WaterNetwork.builder()
                .addSource(source)
                .addDemand(demand)
                .connectNodes(source, demand, pipe)
                .build();
    }

    private WaterSource createSource(String name, double x, double y, double elevation) {
        return WaterSource.builder()
                .name(name)
                .coordinates(CartesianCoordinates.of(x, y, 0))
                .elevation(Elevation.manual(elevation))
                .capacity(Capacity.of(1000.0))
                .flowRate(FlowRate.of(10.0))
                .headPressure(HeadPressure.of(101325.0))
                .build();
    }

    private WaterDemand createDemand(String name, double x, double y, double elevation) {
        return WaterDemand.builder()
                .name(name)
                .coordinates(CartesianCoordinates.of(x, y, 0))
                .elevation(Elevation.manual(elevation))
                .demand(FlowRate.of(5.0))
                .minPressure(HeadPressure.of(50000.0))
                .build();
    }
}
