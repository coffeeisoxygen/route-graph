package com.coffeecode.domain.node.client.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.NetworkNode;

@DisplayName("ConnectionManager Tests")
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
        destIdentity = NetworkIdentity.create(NodeType.SERVER);

        when(mockEdge.getDestination()).thenReturn(mockDestination);
        when(mockDestination.getIdentity()).thenReturn(destIdentity);
    }

    @Nested
    @DisplayName("Connection Management")
    class ConnectionTests {
        @Test
        @DisplayName("Should add valid connection")
        void shouldAddValidConnection() {
            assertThat(manager.addConnection(mockEdge)).isTrue();
            assertThat(manager.getConnectionCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should reject null connection")
        void shouldRejectNullConnection() {
            assertThat(manager.addConnection(null)).isFalse();
            assertThat(manager.getConnectionCount()).isZero();
        }

        @Test
        @DisplayName("Should reject when inactive")
        void shouldRejectWhenInactive() {
            manager.clear();
            assertThat(manager.addConnection(mockEdge)).isFalse();
        }
    }

    @Nested
    @DisplayName("State Management")
    class StateTests {
        @Test
        @DisplayName("Should clear connections on deactivate")
        void shouldClearConnectionsOnDeactivate() {
            manager.addConnection(mockEdge);
            manager.clear();

            assertThat(manager.isActive()).isFalse();
            assertThat(manager.getConnectionCount()).isZero();
        }
    }
}
