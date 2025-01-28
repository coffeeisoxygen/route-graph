package com.coffeecode.domain.node;

import lombok.Getter;

@Getter
public class ClientNode extends AbstractNode {
    private final double dataRate;
    private double currentUsage;

    public ClientNode(String id, double dataRate) {
        super(id, NodeType.CLIENT);
        this.dataRate = dataRate;
        this.currentUsage = 0;
    }

    public boolean canTransmit(double dataSize) {
        return (currentUsage + dataSize) <= dataRate;
    }
}
