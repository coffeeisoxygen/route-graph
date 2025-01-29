package com.coffeecode.domain.node.router.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MetricsSnapshotTest {

    @Test
    public void testIsValid_withValidMetrics() {
        MetricsSnapshot metricsSnapshot = new MetricsSnapshot(10.0, 5, 15.0);
        assertThat(metricsSnapshot.isValid()).isTrue();
    }

    @Test
    public void testIsValid_withNegativeSampleCount() {
        MetricsSnapshot metricsSnapshot = new MetricsSnapshot(10.0, -1, 15.0);
        assertThat(metricsSnapshot.isValid()).isFalse();
    }

    @Test
    public void testIsValid_withNaNAverage() {
        MetricsSnapshot metricsSnapshot = new MetricsSnapshot(Double.NaN, 5, 15.0);
        assertThat(metricsSnapshot.isValid()).isFalse();
    }

    @Test
    public void testIsValid_withZeroSampleCount() {
        MetricsSnapshot metricsSnapshot = new MetricsSnapshot(10.0, 0, 15.0);
        assertThat(metricsSnapshot.isValid()).isTrue();
    }
}
