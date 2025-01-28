package com.coffeecode.domain.edge;

import com.coffeecode.domain.node.Node;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Edge {
    @NonNull
    private Node source;
    @NonNull
    private Node destination;
    private double bandwidth;
    private double latency;
    private boolean active;
    private boolean isActive;

    public boolean isValid() {
        return source != null &&
                destination != null &&
                bandwidth > 0 &&
                latency >= 0;
    }

    public double getWeight() {
        return (1 / bandwidth) * latency;
    }

    public boolean isConnected() {
        return active && source.isActive() && destination.isActive();
    }

    public boolean isActive() {

        return active;

    }

    public void setActive(boolean active) {

        this.active = active;

    }
}
