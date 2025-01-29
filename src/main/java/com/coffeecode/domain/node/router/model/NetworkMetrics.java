package com.coffeecode.domain.node.router.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.Getter;

/**
 * Thread-safe implementation of network metrics collection using sliding
 * window.
 */
@Getter
public class NetworkMetrics {
    private static final int WINDOW_SIZE = 10;
    private static final long MAX_SAMPLE_AGE_MS = 60_000; // 1 minute

    private final Queue<MetricSample> samples;
    private final ReadWriteLock lock;

    public NetworkMetrics() {
        this.samples = new ArrayDeque<>(WINDOW_SIZE);
        this.lock = new ReentrantReadWriteLock();
    }

    public void updateMetric(double value) {
        validateMetric(value);
        lock.writeLock().lock();
        try {
            removeExpiredSamples();
            if (samples.size() >= WINDOW_SIZE) {
                samples.poll();
            }
            samples.offer(new MetricSample(value));
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void validateMetric(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid metric value");
        }
    }

    private void removeExpiredSamples() {
        long currentTime = System.currentTimeMillis();
        while (!samples.isEmpty() &&
                isExpired(samples.peek(), currentTime)) {
            samples.poll();
        }
    }

    private boolean isExpired(MetricSample sample, long currentTime) {
        return currentTime - sample.getTimestamp() > MAX_SAMPLE_AGE_MS;
    }

    public MetricsSnapshot getMetrics() {
        lock.readLock().lock();
        try {
            removeExpiredSamples();
            return new MetricsSnapshot(
                    getAverageMetric(),
                    samples.size(),
                    samples.isEmpty() ? 0 : samples.peek().getTimestamp());
        } finally {
            lock.readLock().unlock();
        }
    }

    public double getAverageMetric() {
        lock.readLock().lock();
        try {
            return samples.stream()
                    .mapToDouble(MetricSample::getValue)
                    .average()
                    .orElse(0.0);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Getter
    private static class MetricSample {
        private final double value;
        private final long timestamp;

        MetricSample(double value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }
}
