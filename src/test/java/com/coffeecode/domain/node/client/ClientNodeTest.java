package com.coffeecode.domain.node.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.client.model.RequestType;
import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

@DisplayName("ClientNode Tests")
class ClientNodeTest {
    private ClientNode client;
    private NodeProperties properties;

    @BeforeEach
    void setUp() {
        properties = NodeProperties.builder()
            .type(NodeType.CLIENT)
            .maxConnections(1)
            .bandwidth(100.0)
            .build();
        client = ClientNode.create(properties);
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperationsTest {
        @Test
        @DisplayName("Should create with valid properties")
        void shouldCreateWithValidProperties() {
            assertThat(client.getProperties()).isEqualTo(properties);
            assertThat(client.isActive()).isTrue();
        }
    }

    @Nested
    @DisplayName("Connection Management")
    class ConnectionTests {
        private RouterNode router;
        private EdgeProperties edgeProps;

        @BeforeEach
        void setUp() {
            NodeProperties routerProps = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();
            router = RouterNode.create(routerProps);

            edgeProps = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build();
        }

        @Test
        @DisplayName("Should connect to router")
        void shouldConnectToRouter() {
            assertThat(client.connect(router, edgeProps)).isTrue();
            assertThat(client.getComponents().getConnections().getConnectionCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not accept incoming connections")
        void shouldNotAcceptConnections() {
            assertThat(client.canAcceptConnection()).isFalse();
        }

        @Test
        @DisplayName("Should respect max connections")
        void shouldRespectMaxConnections() {
            // Given first connection
            client.connect(router, edgeProps);

            // When trying second connection
            RouterNode router2 = RouterNode.create(properties);
            boolean result = client.connect(router2, edgeProps);

            // Then
            assertThat(result).isFalse();
            assertThat(client.getComponents().getConnections().getConnectionCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should not connect to inactive router")
        void shouldNotConnectToInactiveRouter() {
            router.setActive(false);
            assertThat(client.connect(router, edgeProps)).isFalse();
        }

        @Test
        @DisplayName("Should not connect with invalid properties")
        void shouldNotConnectWithInvalidProps() {
            EdgeProperties invalidProps = EdgeProperties.builder()
                .bandwidth(-1.0)
                .latency(-1.0)
                .build();
            assertThat(client.connect(router, invalidProps)).isFalse();
        }
    }

    @Nested
    @DisplayName("State Management")
    class StateTests {
        @Test
        @DisplayName("Should manage active state")
        void shouldManageActiveState() {
            // When deactivated
            client.setActive(false);

            // Then
            assertThat(client.isActive()).isFalse();
            assertThat(client.canInitiateConnection()).isFalse();

            // When reactivated
            client.setActive(true);

            // Then
            assertThat(client.isActive()).isTrue();
            assertThat(client.canInitiateConnection()).isTrue();
        }
    }

    @Nested
    @DisplayName("Request Operations")
    class RequestTests {
        private RouterNode router;
        private EdgeProperties edgeProps;

        @BeforeEach
        void setUp() {
            NodeProperties routerProps = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();
            router = RouterNode.create(routerProps);

            edgeProps = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build();
        }

        @Test
        @DisplayName("Should queue valid request")
        void shouldQueueValidRequest() {
            client.connect(router, edgeProps);
            boolean result = client.queueRequest(
                router.getIdentity(),
                RequestType.DATA
            );
            assertThat(result).isTrue();
            assertThat(client.getComponents().getRequests()
                .getPendingRequests()).hasSize(1);
        }

        @Test
        @DisplayName("Should not queue request when disconnected")
        void shouldNotQueueWhenDisconnected() {
            boolean result = client.queueRequest(
                router.getIdentity(),
                RequestType.DATA
            );
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Request Management")
    class MoreRequestTests {
        private RouterNode router;

        @BeforeEach
        void setUp() {
            router = RouterNode.create(properties);
            client.connect(router, EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build());
        }

        @Test
        @DisplayName("Should queue request to connected node")
        void shouldQueueRequestToConnectedNode() {
            assertThat(client.queueRequest(router.getIdentity(),
                RequestType.CONNECT)).isTrue();
        }

        @Test
        @DisplayName("Should reject request to disconnected node")
        void shouldRejectRequestToDisconnectedNode() {
            RouterNode other = RouterNode.create(properties);
            assertThat(client.queueRequest(other.getIdentity(),
                RequestType.CONNECT)).isFalse();
        }
    }

    @Nested
    @DisplayName("Component State")
    class ComponentStateTests {
        private RouterNode router;
        private EdgeProperties edgeProps;

        @BeforeEach
        void setUp() {
            NodeProperties routerProps = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();
            router = RouterNode.create(routerProps);

            edgeProps = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build();
        }

        @Test
        @DisplayName("Should clear all components")
        void shouldClearAllComponents() {
            // Given
            client.connect(router, edgeProps);
            client.queueRequest(router.getIdentity(), RequestType.DATA);

            // When
            client.setActive(false);

            // Then
            assertThat(client.getComponents().getConnections()
                .getConnectionCount()).isZero();
            assertThat(client.getComponents().getRequests()
                .getPendingRequests()).isEmpty();
        }
    }
}
