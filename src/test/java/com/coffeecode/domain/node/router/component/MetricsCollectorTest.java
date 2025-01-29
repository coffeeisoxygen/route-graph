package com.coffeecode.domain.node.router.component;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;

class MetricsCollectorTest {
    private MetricsCollector collector;
    private NetworkIdentity identity;

    @BeforeEach
    void setUp() {
        collector = new MetricsCollector();
        collector.initialize();
        identity = NetworkIdentity.create(NodeType.ROUTER);
    }

    @Test
    void updateMetrics_WhenActive_ShouldStoreMetrics() {
        collector.updateMetrics(identity, 10.0);

        assertThat(collector.getMetrics())
                .containsKey(identity)
                .hasSize(1);

        var metrics = collector.getMetrics().get(identity).getMetrics();
        assertThat(metrics.getAverage()).isEqualTo(10.0);
    }

    @Test
    void updateMetrics_WhenInactive_ShouldNotStoreMetrics() {
        collector.clear();
        collector.updateMetrics(identity, 10.0);

        assertThat(collector.getMetrics()).isEmpty();
    }

    @Test
    void clear_ShouldRemoveAllMetrics() {
        collector.updateMetrics(identity, 10.0);
        collector.clear();

        assertThat(collector.getMetrics()).isEmpty();
        assertThat(collector.isActive()).isFalse();
    }
}
