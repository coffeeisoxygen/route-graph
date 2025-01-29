package com.coffeecode.domain.node.router.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.router.model.NetworkMetrics;

import lombok.Getter;

@Getter
public class MetricsCollector implements RouterComponent {
    private final Map<NetworkIdentity, NetworkMetrics> metrics;
    private volatile boolean active;

    public MetricsCollector() {
        this.metrics = new ConcurrentHashMap<>();
    }

    @Override
    public void initialize() {
        active = true;
    }

    @Override
    public void clear() {
        metrics.clear();
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void updateMetrics(NetworkIdentity id, double value) {
        if (!active)
            return;
        metrics.computeIfAbsent(id, k -> new NetworkMetrics())
                .updateMetric(value);
    }
}
