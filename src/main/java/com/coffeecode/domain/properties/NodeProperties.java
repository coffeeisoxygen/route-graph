package com.coffeecode.domain.properties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.coffeecode.domain.model.NodeType;

import lombok.Value;

@Value
public class NodeProperties implements NetworkProperties {
    @Min(0)
    int maxConnections;

    @Min(0)
    double bandwidth;

    @NotNull
    NodeType type;

    @Override
    public boolean isValid() {
        return maxConnections >= 0 && bandwidth >= 0 && type != null;
    }
}
