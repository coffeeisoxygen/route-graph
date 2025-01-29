package com.coffeecode.domain.node.router.model;

import lombok.Value;

@Value
public class MetricsSnapshot {
    double average;
    int sampleCount;
    long timestamp;

    public static MetricsSnapshot empty() {
        return new MetricsSnapshot(0.0, 0, System.currentTimeMillis());
    }
}
