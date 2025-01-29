package com.coffeecode.domain.node.router.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.base.RouterNodeTest;
import com.coffeecode.domain.node.router.model.MetricsSnapshot;

@DisplayName("Router Metrics Tests")
class RouterMetricsTest extends RouterNodeTest {
    private NetworkIdentity target;
    private NetworkIdentity nextHop;

    @BeforeEach
    void setUpMetrics() {
        target = NetworkIdentity.create(NodeType.SERVER);
        nextHop = NetworkIdentity.create(NodeType.ROUTER);
    }

    @Test
    @DisplayName("Should track routing metrics")
    void shouldTrackRoutingMetrics() {
        // When
        routerNode.updateRoute(target, nextHop, 10.0);
        routerNode.updateRoute(target, nextHop, 20.0);

        // Then
        MetricsSnapshot metrics = routerNode.getMetricsFor(target);
        assertThat(metrics)
            .isNotNull()
            .satisfies(m -> {
                assertThat(m.getAverage()).isEqualTo(15.0);
                assertThat(m.getSampleCount()).isEqualTo(2);
            });
    }

    @Test
    @DisplayName("Should maintain metrics window size")
    void shouldMaintainMetricsWindow() {
        // Given
        int windowSize = 10;

        // When
        for (int i = 0; i < windowSize + 5; i++) {
            routerNode.updateRoute(target, nextHop, i);
        }

        // Then
        MetricsSnapshot metrics = routerNode.getMetricsFor(target);
        assertThat(metrics.getSampleCount()).isEqualTo(windowSize);
    }
}
