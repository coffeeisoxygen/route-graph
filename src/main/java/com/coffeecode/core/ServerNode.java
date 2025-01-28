package com.coffeecode.core;

import lombok.Getter;

@Getter
public class ServerNode extends AbstractNode {
    private final int capacity;
    private final double processingPower;
    private int currentLoad;

    public ServerNode(String id, int capacity, double processingPower) {
        super(id, NodeType.SERVER);
        this.capacity = capacity;
        this.processingPower = processingPower;
        this.currentLoad = 0;
    }

    public boolean canHandleRequest() {
        return currentLoad < capacity;
    }
}
