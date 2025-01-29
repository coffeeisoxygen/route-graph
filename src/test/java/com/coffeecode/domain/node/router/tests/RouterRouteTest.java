package com.coffeecode.domain.node.router.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.base.RouterNodeTest;
import com.coffeecode.domain.node.router.model.RouteInfo;

@DisplayName("Router Route Tests")
class RouterRouteTest extends RouterNodeTest {
    private NetworkIdentity destination;
    private NetworkIdentity nextHop;
    private double metric;

    @BeforeEach
    void setUpRoutes() {
        destination = NetworkIdentity.create(NodeType.SERVER);
        nextHop = NetworkIdentity.create(NodeType.ROUTER);
        metric = 10.0;
        routerNode.setActive(true);
    }

    @Test
    @DisplayName("Should update and find route")
    void shouldUpdateAndFindRoute() {
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
    @DisplayName("Should validate route metrics")
    void shouldValidateRouteMetrics() {
        assertThatThrownBy(() ->
            routerNode.updateRoute(destination, nextHop, -1.0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid metric");
    }
}
