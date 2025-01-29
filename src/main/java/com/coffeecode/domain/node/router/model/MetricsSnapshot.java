package com.coffeecode.domain.node.router.model;

import lombok.Value;

@Value
public class MetricsSnapshot {
    double average;
    int sampleCount;
    double latestValue;

    // Add validation
    public boolean isValid() {
        return sampleCount >= 0 && !Double.isNaN(average);
    }
}
