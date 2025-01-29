package com.coffeecode.domain.node.properties;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.coffeecode.domain.node.base.NodeType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RouterNodeProperties extends BaseNodeProperties {
    @Positive
    private final int routingCapacity;
    private final double bufferSize;
    private final boolean supportsQos;

    @Builder
    public RouterNodeProperties(UUID id, String name, String description,
            @NotNull @Positive int routingCapacity,
            double bufferSize, boolean supportsQos) {
        super(id, name, description, NodeType.ROUTER, true);
        this.routingCapacity = routingCapacity;
        this.bufferSize = bufferSize;
        this.supportsQos = supportsQos;
    }

    @Override
    public boolean isValid() {
        return super.isValid()
                && routingCapacity > 0;
    }
}
