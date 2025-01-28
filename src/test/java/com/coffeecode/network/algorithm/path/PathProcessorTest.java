package com.coffeecode.network.algorithm.path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.network.core.WaterNetwork;
import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.edges.PipeMaterial;
import com.coffeecode.network.nodes.WaterNodes;

@ExtendWith(MockitoExtension.class)
@DisplayName("PathProcessor Tests")
class PathProcessorTest {
    private PathProcessor processor;

    @Mock private WaterNetwork network;
    @Mock private WaterNodes currentNode;
    @Mock private WaterNodes neighborNode;
    private Map<WaterNodes, PathInfo> pathInfo;
    private PriorityQueue<WaterNodes> queue;


    @BeforeEach
    void setUp() {
        processor = PathProcessor.createInstance();
        pathInfo = new HashMap<>();
        queue = new PriorityQueue<>((a, b) ->
            Double.compare(pathInfo.get(a).getDistance(), pathInfo.get(b).getDistance()));

        // Initialize pathInfo
        pathInfo.put(currentNode, new PathInfo());
        pathInfo.put(neighborNode, new PathInfo());
        pathInfo.get(currentNode).setDistance(0.0);
    }

    @Nested
    @DisplayName("Process Neighbors Tests")
    class ProcessNeighborsTests {
        @Test
        @DisplayName("Should process valid neighbors")
        void shouldProcessValidNeighbors() {
            // Given
            Map<WaterNodes, Pipe> neighbors = new HashMap<>();
            Pipe pipe = new Pipe(0.1, 10.0, PipeMaterial.PVC);
            neighbors.put(neighborNode, pipe);

            when(network.getAdjacencyMap()).thenReturn(Map.of(currentNode, neighbors));
            when(currentNode.getElevation().getMeters()).thenReturn(0.0);
            when(neighborNode.getElevation().getMeters()).thenReturn(5.0);

            // When
            processor.processNeighbors(network, currentNode, pathInfo, queue);

            // Then
            assertTrue(queue.contains(neighborNode));
            assertEquals(20.0, pathInfo.get(neighborNode).getDistance(), 0.001);
        }

        @Test
        @DisplayName("Should handle nodes with no neighbors")
        void shouldHandleNoNeighbors() {
            // Given
            when(network.getAdjacencyMap()).thenReturn(Map.of(currentNode, new HashMap<>()));

            // When
            processor.processNeighbors(network, currentNode, pathInfo, queue);

            // Then
            assertTrue(queue.isEmpty());
        }
    }

    @Nested
    @DisplayName("Weight Calculation Tests")
    class WeightCalculationTests {
        @Test
        @DisplayName("Should calculate uphill weights correctly")
        void shouldCalculateUphillWeights() {
            // Setup for uphill calculation
            // ... test implementation
        }

        @Test
        @DisplayName("Should calculate downhill weights correctly")
        void shouldCalculateDownhillWeights() {
            // Setup for downhill calculation
            // ... test implementation
        }
    }
}
