package com.coffeecode.domain.entities.node.impl;

import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

/**
 * Properties specific to router nodes.
 * Defines routing capabilities and buffer configurations.
 */
@Getter
@Builder
public class RouterNodeProperties {
    @Positive
    private final int routingCapacity;
    @Positive
    private final double bufferSize;
    private final boolean supportsQos;

    public boolean isValid() {
        return routingCapacity > 0 && bufferSize > 0;
    }
}
