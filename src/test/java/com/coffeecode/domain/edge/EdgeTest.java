package com.coffeecode.domain.edge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.edge.EdgeProperties;
import com.coffeecode.domain.entities.node.base.Node;

@ExtendWith(MockitoExtension.class)
class EdgeTest {

    @Mock
    private Node sourceNode;

    @Mock
    private Node destNode;

    @Mock
    private EdgeProperties properties;

    private Edge edge;

    @BeforeEach
    void setUp() {
        when(properties.getBandwidth()).thenReturn(100.0);
        when(properties.getLatency()).thenReturn(5.0);
        when(properties.isValid()).thenReturn(true);

        edge = Edge.builder()
                .source(sourceNode)
                .destination(destNode)
                .properties(properties)
                .active(true)
                .build();
    }

    @Test
    void shouldCalculateCorrectWeight() {
        double expectedWeight = (1 / 100.0) * 5.0;
        assertEquals(expectedWeight, edge.getWeight());
    }

    @Test
    void shouldBeConnectedWhenAllNodesActive() {
        when(sourceNode.isActive()).thenReturn(true);
        when(destNode.isActive()).thenReturn(true);

        assertTrue(edge.isConnected());
    }

    @Test
    void shouldNotBeConnectedWhenSourceInactive() {
        when(sourceNode.isActive()).thenReturn(false);
        when(destNode.isActive()).thenReturn(true);

        assertFalse(edge.isConnected());
    }

    @Test
    void shouldValidateEdge() {
        assertTrue(edge.isValid());
    }

    @Test
    void shouldInvalidateEdgeWithNullProperties() {
        Edge invalidEdge = Edge.builder()
                .source(sourceNode)
                .destination(destNode)
                .properties(null)
                .build();

        assertThrows(NullPointerException.class, invalidEdge::isValid);
    }
}
