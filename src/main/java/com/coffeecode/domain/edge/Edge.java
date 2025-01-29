package com.coffeecode.domain.edge;

import com.coffeecode.domain.edge.properties.EdgeProperties;
import com.coffeecode.domain.node.model.Node;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Edge {
    @NonNull
    private final Node source;
    @NonNull
    private final Node destination;
    @NonNull
    private final EdgeProperties properties;
    private boolean active;

    public boolean isValid() {
        return source != null &&
                destination != null &&
                properties != null &&
                properties.isValid();
    }

    public double getWeight() {
        return (1 / properties.getBandwidth()) * properties.getLatency();
    }

    public boolean isConnected() {
        return active && source.isActive() && destination.isActive();
    }

    public double getBandwidth() {
        return properties.getBandwidth();
    }

    public double getLatency() {
        return properties.getLatency();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
