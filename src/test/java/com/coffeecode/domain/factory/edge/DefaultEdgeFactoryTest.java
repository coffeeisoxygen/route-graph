package com.coffeecode.domain.factory.edge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

@ExtendWith(MockitoExtension.class)
class DefaultEdgeFactoryTest {
    @Mock
    private NetNode sourceNode;
    @Mock
    private NetNode targetNode;
    @Mock
    private NetEdgeProperties properties;

    private DefaultEdgeFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DefaultEdgeFactory();

        // Setup lenient stubs for common behaviors
        lenient().when(sourceNode.isActive()).thenReturn(true);
        lenient().when(targetNode.isActive()).thenReturn(true);
        lenient().when(properties.isValid()).thenReturn(true);
        lenient().when(properties.getBandwidth()).thenReturn(100.0);
        lenient().when(properties.getLatency()).thenReturn(10.0);
    }

    @Test
    void shouldCreateEdge() {
        // When
        NetEdge edge = factory.createEdge(sourceNode, targetNode, properties);

        // Then
        assertNotNull(edge);
        assertNotNull(edge.getIdentity());
        assertEquals(sourceNode, edge.getSource());
        assertEquals(targetNode, edge.getDestination());
        assertTrue(edge.isActive());
    }

    @Test
    void shouldCreateBidirectionalEdges() {
        // When
        var edges = factory.createBidirectional(sourceNode, targetNode, properties);

        // Then
        assertEquals(2, edges.size());
        assertTrue(edges.stream().allMatch(e -> e.getIdentity() != null));
        assertEquals(sourceNode, edges.get(0).getSource());
        assertEquals(targetNode, edges.get(1).getSource());
    }

    @Test
    void shouldValidateEdgeCreation() {
        // Given invalid properties
        when(properties.isValid()).thenReturn(false);

        // Then should throw for invalid properties
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEdge(sourceNode, targetNode, properties));

        // Given inactive source node
        when(properties.isValid()).thenReturn(true);
        when(sourceNode.isActive()).thenReturn(false);

        // Then should throw for inactive node
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEdge(sourceNode, targetNode, properties));
    }

    @Test
    void shouldValidateNullValues() {
        // Test null properties
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEdge(sourceNode, targetNode, null));

        // Test null source
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEdge(null, targetNode, properties));

        // Test null target
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEdge(sourceNode, null, properties));
    }
}
