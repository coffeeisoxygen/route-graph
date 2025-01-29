package com.coffeecode.domain.node.router.component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.router.model.MetricsSnapshot;

import lombok.Getter;

@Getter
public class MetricsCollector implements RouterComponent {
    private static final int WINDOW_SIZE = 10;
    private final Map<NetworkIdentity, MetricsWindow> metricsWindows;
    private volatile boolean active;

    public MetricsCollector() {
        this.metricsWindows = new ConcurrentHashMap<>();
        this.active = true;
    }

    public Optional<MetricsSnapshot> getMetricsFor(NetworkIdentity target) {
        if (!active || target == null) {
            return Optional.empty();
        }

        MetricsWindow window = metricsWindows.get(target);
        return window != null ? Optional.of(window.getSnapshot()) : Optional.empty();
    }

    public void updateMetric(NetworkIdentity target, double metric) {
        if (!active || target == null)
            return;

        metricsWindows.computeIfAbsent(target, k -> new MetricsWindow(WINDOW_SIZE))
                .addMetric(metric);
    }

    @Override
    public void initialize() {
        active = true;
    }

    @Override
    public void clear() {
        metricsWindows.clear();
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * Clears metrics for specified destination
     */
    public void clearMetrics(NetworkIdentity destination) {
        if (destination != null) {
            metricsWindows.remove(destination);
        }
    }
}
