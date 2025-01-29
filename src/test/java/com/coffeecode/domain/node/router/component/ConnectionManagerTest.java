package com.coffeecode.domain.node.router.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.NetworkNode;

class ConnectionManagerTest {
    private ConnectionManager manager;
    private NetworkEdge mockEdge;
    private NetworkNode mockDestination;
    private NetworkIdentity destIdentity;

    @BeforeEach
    void setUp() {
        manager = new ConnectionManager();
        manager.initialize();

        mockEdge = mock(NetworkEdge.class);
        mockDestination = mock(NetworkNode.class);
        destIdentity = NetworkIdentity.create(NodeType.ROUTER);

        when(mockEdge.getDestination()).thenReturn(mockDestination);
        when(mockDestination.getIdentity()).thenReturn(destIdentity);
        when(mockEdge.isActive()).thenReturn(true);
    }

    @Test
    void addConnection_WhenActive_ShouldAddConnection() {
        assertThat(manager.addConnection(mockEdge)).isTrue();
        assertThat(manager.getConnectionCount()).isEqualTo(1);
    }

    @Test
    void addConnection_WhenInactive_ShouldRejectConnection() {
        manager.clear();  // This sets the manager to inactive
        assertThat(manager.isActive()).isFalse(); // Verify inactive state
        assertThat(manager.addConnection(mockEdge)).isFalse();
        assertThat(manager.getConnectionCount()).isZero();
    }

    @Test
    void isNodeReachable_WithActiveConnection_ShouldReturnTrue() {
        manager.addConnection(mockEdge);
        assertThat(manager.isNodeReachable(destIdentity)).isTrue();
    }

    @Test
    void removeConnection_ExistingConnection_ShouldRemoveAndUpdateCount() {
        manager.addConnection(mockEdge);
        assertThat(manager.removeConnection(mockEdge)).isTrue();
        assertThat(manager.getConnectionCount()).isZero();
    }
}
