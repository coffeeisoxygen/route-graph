package com.coffeecode.domain.edge;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class NetEdge {
    @NonNull
    private final Identity identity;
    @NonNull
    private final NetNode source;
    @NonNull
    private final NetNode destination;
    @NonNull
    private final NetEdgeProperties properties;
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
