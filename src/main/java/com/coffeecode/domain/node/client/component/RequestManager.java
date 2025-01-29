package com.coffeecode.domain.node.client.component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.client.model.ClientMetrics;
import com.coffeecode.domain.node.client.model.NetworkRequest;
import com.coffeecode.domain.node.client.model.RequestType;

import lombok.Getter;

@Getter
public class RequestManager implements ClientComponent {
    private volatile boolean active;
    private final Queue<NetworkRequest> pendingRequests;
    private final ClientMetrics metrics;

    public RequestManager() {
        this.pendingRequests = new ConcurrentLinkedQueue<>();
        this.metrics = new ClientMetrics();
    }

    public boolean queueRequest(NetworkIdentity target, RequestType type) {
        if (!isActive()) {
            metrics.getRequestMetrics().recordFailure();
            return false;
        }

        NetworkRequest request = NetworkRequest.builder()
                .target(target)
                .type(type)
                .build();

        if (request.isValid() && pendingRequests.offer(request)) {
            metrics.getRequestMetrics().recordSuccess();
            return true;
        }

        metrics.getRequestMetrics().recordFailure();
        return false;
    }

    @Override
    public void initialize() {
        pendingRequests.clear();
        metrics.reset();
        active = true;
    }

    @Override
    public void clear() {
        active = false;
        pendingRequests.clear();
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
