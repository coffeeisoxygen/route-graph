package com.coffeecode.domain.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

@ExtendWith(MockitoExtension.class)
class DefaultConnectionManagerTest {
    @Mock
    private NetNode owner;
    @Mock
    private NetNode otherNode;
    @Mock
    private NetEdge edge;

    private DefaultConnectionManager manager;

    @BeforeEach
    void setUp() {
        manager = new DefaultConnectionManager(owner);
        lenient().when(edge.isValid()).thenReturn(true);
        lenient().when(edge.getSource()).thenReturn(owner);
        lenient().when(edge.getDestination()).thenReturn(otherNode);
    }

    @Test
    void shouldAddValidConnection() {
        // When
        manager.addConnection(edge);

        // Then
        assertEquals(1, manager.getConnectionCount());
        assertTrue(manager.hasConnection(edge));
    }

    @Test
    void shouldRejectDuplicateConnection() {
        // Given
        manager.addConnection(edge);

        // Then
        assertThrows(IllegalArgumentException.class,
                () -> manager.addConnection(edge));
    }

    @Test
    void shouldEnforceMaxConnections() {
        // Given
        manager.addConnection(edge);

        // When/Then
        manager.validateMaxConnections(2); // Should pass
        assertThrows(IllegalStateException.class,
                () -> manager.validateMaxConnections(1)); // Should fail
    }

    @Test
    void shouldFindExistingConnection() {
        // Given
        manager.addConnection(edge);

        // When/Then
        assertTrue(manager.findConnection(otherNode).isPresent());
        assertTrue(manager.isConnectedTo(otherNode));
    }
}
