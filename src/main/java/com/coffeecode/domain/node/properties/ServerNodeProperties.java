package com.coffeecode.domain.node.properties;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.coffeecode.domain.node.base.NodeType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ServerNodeProperties extends BaseNodeProperties {
    @Positive
    private final int capacity;
    @Positive
    private final double processingPower;
    private final int maxConnections;

    @Builder
    public ServerNodeProperties(UUID id, String name, String description,
            @NotNull @Positive int capacity,
            @NotNull @Positive double processingPower,
            int maxConnections) {
        super(id, name, description, NodeType.SERVER, true);
        this.capacity = capacity;
        this.processingPower = processingPower;
        this.maxConnections = maxConnections;
    }

    @Override
    public boolean isValid() {
        return super.isValid()
                && capacity > 0
                && processingPower > 0;
    }
}
