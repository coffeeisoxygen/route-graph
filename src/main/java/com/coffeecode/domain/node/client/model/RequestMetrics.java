package com.coffeecode.domain.node.client.model;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;

@Getter
public class RequestMetrics {
    private final AtomicLong totalRequests;
    private final AtomicLong successfulRequests;
    private final AtomicLong failedRequests;

    public RequestMetrics() {
        this.totalRequests = new AtomicLong();
        this.successfulRequests = new AtomicLong();
        this.failedRequests = new AtomicLong();
    }

    public void recordSuccess() {
        totalRequests.incrementAndGet();
        successfulRequests.incrementAndGet();
    }

    public void recordFailure() {
        totalRequests.incrementAndGet();
        failedRequests.incrementAndGet();
    }

    public void reset() {
        totalRequests.set(0);
        successfulRequests.set(0);
        failedRequests.set(0);
    }
}
