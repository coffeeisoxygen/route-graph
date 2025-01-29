package com.coffeecode.domain.entities.node.impl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

/**
 * Properties specific to client nodes.
 * Defines data transmission capabilities and bandwidth limits.
 */
@Getter
@Builder
public class ClientNodeProperties {
    @NotNull
    @Positive
    private final Double dataRate;

    @Positive
    private final Double maxBandwidth;

    /**
     * Validates client node properties
     *
     * @return true if properties are valid
     */
    public boolean isValid() {
        return dataRate != null &&
                dataRate > 0 &&
                (maxBandwidth == null || maxBandwidth > 0);
    }
}
