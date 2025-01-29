package com.coffeecode.edge;

import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.EdgeProperties;

import lombok.Value;

@Value
public class NetworkEdge {
    NetworkNode source;
    NetworkNode destination;
    EdgeProperties properties;
    boolean active;

    public boolean isValid() {
        return source != null &&
                destination != null &&
                properties != null &&
                properties.isValid();
    }
}
