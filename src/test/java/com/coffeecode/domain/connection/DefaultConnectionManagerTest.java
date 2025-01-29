package com.coffeecode.domain.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;

@ExtendWith(MockitoExtension.class)
class DefaultConnectionManagerTest {
    @Mock
    private Node owner;
    @Mock
    private Node otherNode;
    @Mock
    private Edge edge;

    private DefaultConnectionManager manager;

    @BeforeEach
    void setUp() {
        manager = new DefaultConnectionManager(owner);
        when(edge.isValid()).thenReturn(true);
        when(edge.getSource()).thenReturn(owner);
        when(edge.getDestination()).thenReturn(otherNode);
    }

    @Test
    void shouldAddValidConnection() {
        manager.addConnection(edge);
        assertEquals(1, manager.getConnectionCount());
        assertTrue(manager.hasConnection(edge));
    }

    @Test
    void shouldRejectInvalidEdge() {
        when(edge.isValid()).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> manager.addConnection(edge));
    }

    @Test
    void shouldRejectDuplicateConnection() {
        manager.addConnection(edge);
        assertThrows(IllegalArgumentException.class,
                () -> manager.addConnection(edge));
    }

    @Test
    void shouldEnforceMaxConnections() {
        manager.addConnection(edge);
        manager.validateMaxConnections(1);
        assertThrows(IllegalStateException.class,
                () -> manager.validateMaxConnections(0));
    }

    @Test
    void shouldFindExistingConnection() {
        manager.addConnection(edge);
        assertTrue(manager.findConnection(otherNode).isPresent());
        assertTrue(manager.isConnectedTo(otherNode));
    }
}
