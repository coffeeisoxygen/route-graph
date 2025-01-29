package com.coffeecode.domain.edge;

import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.EdgeProperties;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NetworkEdge {
    NetworkNode source;
    NetworkNode destination;
    EdgeProperties properties;

    @Builder.Default
    boolean active = true;

    public boolean isValid() {
        return source != null &&
                destination != null &&
                properties != null &&
                properties.isValid() &&
                !source.equals(destination);
    }
}
