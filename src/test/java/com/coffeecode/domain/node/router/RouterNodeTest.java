package com.coffeecode.domain.node.router;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.model.RouteInfo;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

@ExtendWith(MockitoExtension.class)
class RouterNodeTest {
    private RouterNode routerNode;
    private NodeProperties properties;

    @BeforeEach
    void setUp() {
        properties = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();
        routerNode = RouterNode.create(properties);
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperationsTests {
        @Test
        @DisplayName("Should create router with valid properties")
        void shouldCreateWithValidProperties() {
            assertThat(routerNode.getProperties()).isEqualTo(properties);
            assertThat(routerNode.isActive()).isTrue();
        }

        @Test
        @DisplayName("Should throw on null properties")
        void shouldThrowOnNullProperties() {
            assertThatThrownBy(() -> RouterNode.create(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Properties cannot be null");
        }
    }

    @Nested
    @DisplayName("Connection Management")
    class ConnectionTests {
        @Test
        @DisplayName("Should respect max connections")
        void shouldRespectMaxConnections() {
            // When
            for (int i = 0; i < properties.getMaxConnections(); i++) {
                boolean added = routerNode.addConnection(createValidEdge());
                assertThat(added).isTrue();
            }

            // Then
            boolean exceeded = routerNode.addConnection(createValidEdge());
            assertThat(exceeded).isFalse();
            assertThat(routerNode.getComponents().getConnections().getConnectionCount())
                    .isEqualTo(properties.getMaxConnections());
        }

        @Test
        @DisplayName("Should reject connections when inactive")
        void shouldRejectConnectionsWhenInactive() {
            routerNode.setActive(false);
            assertThat(routerNode.addConnection(createValidEdge())).isFalse();
        }
    }

    @Nested
    @DisplayName("Route Management")
    class RouteTests {
        private NetworkIdentity destination;
        private RouterNode nextHopNode;
        private double metric;

        @BeforeEach
        void setUp() {
            destination = NetworkIdentity.create(NodeType.SERVER);
            nextHopNode = RouterNode.create(properties);
            metric = 10.0;

            // Create and add connection to nextHop
            NetworkEdge edge = createEdge(routerNode, nextHopNode);
            routerNode.addConnection(edge);
        }

        @Test
        @DisplayName("Should update and find route")
        void shouldUpdateAndFindRoute() {
            // When
            routerNode.updateRoute(destination, nextHopNode.getIdentity(), metric);

            // Then
            Optional<RouteInfo> route = routerNode.findRoute(destination);
            assertThat(route)
                    .isPresent()
                    .hasValueSatisfying(r -> {
                        assertThat(r.getNextHop()).isEqualTo(nextHopNode.getIdentity());
                        assertThat(r.getMetric()).isEqualTo(metric);
                    });
        }

        @Test
        @DisplayName("Should not find route when inactive")
        void shouldNotFindRouteWhenInactive() {
            // Given
            routerNode.updateRoute(destination, nextHopNode.getIdentity(), metric);

            // When
            routerNode.setActive(false);

            // Then
            assertThat(routerNode.findRoute(destination)).isEmpty();
        }

        @Test
        @DisplayName("Should not update route when inactive")
        void shouldNotUpdateRouteWhenInactive() {
            // Given
            NetworkIdentity destination = NetworkIdentity.create(NodeType.SERVER);
            NetworkIdentity nextHop = NetworkIdentity.create(NodeType.ROUTER);
            routerNode.setActive(false);

            // When
            routerNode.updateRoute(destination, nextHop, 10.0);

            // Then
            assertThat(routerNode.findRoute(destination)).isEmpty();
        }

        private NetworkEdge createEdge(RouterNode source, RouterNode target) {
            return NetworkEdge.builder()
                    .source(source)
                    .destination(target)
                    .properties(EdgeProperties.builder()
                            .bandwidth(100.0)
                            .latency(10.0)
                            .build())
                    .active(true)
                    .build();
        }
    }

    private NetworkEdge createValidEdge() {
        RouterNode destination = RouterNode.create(properties);
        EdgeProperties edgeProps = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build();

        return NetworkEdge.builder()
                .source(routerNode)
                .destination(destination)
                .properties(edgeProps)
                .active(true)
                .build();
    }
}
