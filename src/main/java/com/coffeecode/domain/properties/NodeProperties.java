package com.coffeecode.domain.properties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.coffeecode.domain.model.NodeType;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NodeProperties implements NetworkProperties {
    @Builder.Default
    @Min(0)
    int maxConnections = 4;

    @Builder.Default
    @Min(0)
    double bandwidth = 100.0;

    @NotNull
    NodeType type;

    @Override
    public boolean isValid() {
        return maxConnections >= 0 &&
                bandwidth >= 0 &&
                type != null;
    }
}
