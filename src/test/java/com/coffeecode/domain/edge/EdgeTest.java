package com.coffeecode.domain.edge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.common.NetID;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

@ExtendWith(MockitoExtension.class)
class EdgeTest {
    @Mock
    private NetNode source;
    @Mock
    private NetNode destination;
    @Mock
    private NetEdgeProperties properties;

    private NetEdge edge;
    private NetID identity;

    @BeforeEach
    void setUp() {
        identity = NetID.create("edge");
        // Use lenient() for setup stubs that might not be used in all tests
        lenient().when(properties.isValid()).thenReturn(true);
        lenient().when(properties.calculateWeight()).thenReturn(10.0);
        lenient().when(source.isActive()).thenReturn(true);
        lenient().when(destination.isActive()).thenReturn(true);

        edge = NetEdge.builder()
                .identity(identity)
                .source(source)
                .destination(destination)
                .properties(properties)
                .active(true)
                .build();
    }

    @Test
    void shouldCreateValidEdge() {
        assertTrue(edge.isValid());
        assertEquals(identity, edge.getIdentity());
        assertEquals(source, edge.getSource());
        assertEquals(destination, edge.getDestination());
        assertEquals(properties, edge.getProperties());
        assertTrue(edge.isActive());
    }

    @Test
    void shouldCalculateWeight() {
        assertEquals(10.0, edge.getWeight());
        verify(properties).calculateWeight();
    }

    @Test
    void shouldCheckConnectionState() {
        when(source.isActive()).thenReturn(true);
        when(destination.isActive()).thenReturn(true);

        assertTrue(edge.isConnected());
    }

    @Test
    void shouldDetectDisconnectedState() {
        when(source.isActive()).thenReturn(false);
        assertFalse(edge.isConnected());

        when(source.isActive()).thenReturn(true);
        when(destination.isActive()).thenReturn(false);
        assertFalse(edge.isConnected());

        // Test when edge is inactive
        edge = NetEdge.builder()
                .identity(identity)
                .source(source)
                .destination(destination)
                .properties(properties)
                .active(false)
                .build();
        assertFalse(edge.isConnected());
    }

    @Test
    void shouldValidateRequiredFields() {
        assertThrows(IllegalArgumentException.class, () -> {
            NetEdge.builder()
                    .identity(null)
                    .source(source)
                    .destination(destination)
                    .properties(properties)
                    .build();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            NetEdge.builder()
                    .identity(identity)
                    .source(null)
                    .destination(destination)
                    .properties(properties)
                    .build();
        });
    }
}
