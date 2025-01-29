package com.coffeecode.domain.node.client.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Getter;

@Getter
public class ClientMetrics {
    private final RequestMetrics requestMetrics;
    private final Map<NetworkIdentity, Double> connectionLatencies;

    public ClientMetrics() {
        this.requestMetrics = new RequestMetrics();
        this.connectionLatencies = new ConcurrentHashMap<>();
    }

    public void recordLatency(NetworkIdentity target, double latency) {
        connectionLatencies.put(target, latency);
    }

    public void reset() {
        requestMetrics.reset();
        connectionLatencies.clear();
    }
}
