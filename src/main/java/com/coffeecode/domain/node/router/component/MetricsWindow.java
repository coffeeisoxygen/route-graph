package com.coffeecode.domain.node.router.component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.coffeecode.domain.node.router.model.MetricsSnapshot;

import lombok.Getter;

@Getter
class MetricsWindow {
    private final Queue<Double> metrics;
    private final int maxSize;

    MetricsWindow(int maxSize) {
        this.maxSize = maxSize;
        this.metrics = new ConcurrentLinkedQueue<>();
    }

    void addMetric(double metric) {
        if (metrics.size() >= maxSize) {
            metrics.poll();
        }
        metrics.offer(metric);
    }

    MetricsSnapshot getSnapshot() {
        if (metrics.isEmpty()) {
            return MetricsSnapshot.empty();
        }

        double average = metrics.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return new MetricsSnapshot(average, metrics.size(), System.currentTimeMillis());
    }
}
