package com.coffeecode.domain.edge;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.properties.EdgeProperties;
import com.coffeecode.domain.node.base.Node;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Edge {
    @NonNull
    private final Identity identity;
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

    public boolean isConnected() {
        return active && source.isActive() && destination.isActive();
    }

    public double getWeight() {
        return properties.calculateWeight();
    }
}
