package com.coffeecode.domain.node.properties;

import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeProperties {
    @Positive
    private final Double dataRate; // For ClientNode
    @Positive
    private final Integer capacity; // For ServerNode
    @Positive
    private final Double processingPower; // For ServerNode
    @Positive
    private final Integer routingCapacity; // For RouterNode
}
