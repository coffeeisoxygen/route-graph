package com.coffeecode.domain.topology.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

@ExtendWith(MockitoExtension.class)
class NetPathFinderTest {
    @Mock
    private NetNode sourceNode;
    @Mock
    private NetNode middleNode;
    @Mock
    private NetNode targetNode;
    @Mock
    private NetEdge edge1;
    @Mock
    private NetEdge edge2;

    private NetGraph graph;
    private NetPathFinder pathFinder;

    @BeforeEach
    void setUp() {
        graph = new NetGraph();
        pathFinder = new NetPathFinder(graph);

        // Setup test nodes
        lenient().when(sourceNode.getIdentity()).thenReturn(Identity.create("source"));
        lenient().when(middleNode.getIdentity()).thenReturn(Identity.create("middle"));
        lenient().when(targetNode.getIdentity()).thenReturn(Identity.create("target"));

        // Setup test edges
        lenient().when(edge1.getSource()).thenReturn(sourceNode);
        lenient().when(edge1.getDestination()).thenReturn(middleNode);
        lenient().when(edge1.getWeight()).thenReturn(1.0);

        lenient().when(edge2.getSource()).thenReturn(middleNode);
        lenient().when(edge2.getDestination()).thenReturn(targetNode);
        lenient().when(edge2.getWeight()).thenReturn(1.0);
    }

    @Test
    void shouldFindExistingPath() {
        // Given
        graph.addNode(sourceNode);
        graph.addNode(middleNode);
        graph.addNode(targetNode);
        graph.addEdge(edge1);
        graph.addEdge(edge2);

        // When
        List<NetEdge> path = pathFinder.findShortestPath(sourceNode, targetNode);

        // Then
        assertEquals(2, path.size());
        assertEquals(edge1, path.get(0));
        assertEquals(edge2, path.get(1));
    }

    @Test
    void shouldReturnEmptyPathWhenNoRouteExists() {
        // Given
        graph.addNode(sourceNode);
        graph.addNode(targetNode);

        // When
        List<NetEdge> path = pathFinder.findShortestPath(sourceNode, targetNode);

        // Then
        assertTrue(path.isEmpty());
    }
}
