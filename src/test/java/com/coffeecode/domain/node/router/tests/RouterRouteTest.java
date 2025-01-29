package com.coffeecode.domain.node.router.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.node.router.base.RouterNodeTest;
import com.coffeecode.domain.node.router.model.RouteInfo;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

@ExtendWith(MockitoExtension.class)
@DisplayName("Router Route Tests")
class RouterRouteTest extends RouterNodeTest {
    private RouterNode routerNode;
    private RouterNode nextHopRouter;
    private NetworkIdentity destination;
    private NetworkIdentity nextHop;
    private double metric;

    @BeforeEach
    void setUp() {
        // Initialize router with properties
        NodeProperties properties = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();

        routerNode = RouterNode.create(properties);
        nextHopRouter = RouterNode.create(properties);

        destination = NetworkIdentity.create(NodeType.SERVER);
        nextHop = nextHopRouter.getIdentity();
        metric = 10.0;
    }

    private void setupValidNextHopConnection() {
        EdgeProperties edgeProps = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build();

        // Using the new connect method
        boolean connected = routerNode.connect(nextHopRouter, edgeProps);
        assertThat(connected).isTrue();
    }

    @Test
    @DisplayName("Should update and find route when next hop is connected")
    void shouldUpdateAndFindRoute() {
        // Given
        setupValidNextHopConnection();

        // When
        routerNode.updateRoute(destination, nextHop, metric);

        // Then
        Optional<RouteInfo> route = routerNode.findRoute(destination);
        assertThat(route)
                .isPresent()
                .hasValueSatisfying(r -> {
                    assertThat(r.getNextHop()).isEqualTo(nextHop);
                    assertThat(r.getMetric()).isEqualTo(metric);
                    assertThat(r.isValid()).isTrue();
                });
    }

    @Test
    @DisplayName("Should not find route when next hop is not connected")
    void shouldNotFindRouteWithoutConnection() {
        // Given
        routerNode.setActive(true);
        assertThat(isNodeReachable(nextHop)).isFalse();

        // When
        routerNode.updateRoute(destination, nextHop, metric);

        // Then
        Optional<RouteInfo> route = routerNode.findRoute(destination);
        assertThat(route).isEmpty();
    }

    private boolean isNodeReachable(NetworkIdentity nodeId) {
        return routerNode.getComponents()
                .getConnections()
                .getConnections()
                .stream()
                .anyMatch(edge -> edge.getDestination()
                        .getIdentity()
                        .equals(nodeId));
    }

    @Test
    @DisplayName("Should validate route metrics")
    void shouldValidateRouteMetrics() {
        assertThatThrownBy(() -> routerNode.updateRoute(destination, nextHop, -1.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid metric");
    }

    @Test
    @DisplayName("Should expire old routes")
    void shouldExpireOldRoutes() throws InterruptedException {
        // Given
        setupValidNextHopConnection();
        routerNode.updateRoute(destination, nextHop, metric);

        // When
        Thread.sleep(ROUTE_EXPIRY_MS + 100);

        // Then
        Optional<RouteInfo> route = routerNode.findRoute(destination);
        assertThat(route).isEmpty();
    }

    @Test
    @DisplayName("Should update existing route")
    void shouldUpdateExistingRoute() {
        // Given
        setupValidNextHopConnection();
        routerNode.updateRoute(destination, nextHop, metric);
        double newMetric = 20.0;

        // When
        routerNode.updateRoute(destination, nextHop, newMetric);

        // Then
        Optional<RouteInfo> route = routerNode.findRoute(destination);
        assertThat(route)
                .isPresent()
                .hasValueSatisfying(r -> assertThat(r.getMetric()).isEqualTo(newMetric));
    }

    @Test
    @DisplayName("Should remove route")
    void shouldRemoveRoute() {
        // Given
        setupValidNextHopConnection();
        routerNode.updateRoute(destination, nextHop, metric);

        // When
        routerNode.removeRoute(destination);

        // Then
        Optional<RouteInfo> route = routerNode.findRoute(destination);
        assertThat(route).isEmpty();
    }

}
