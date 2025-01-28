package com.coffeecode.domain.node;

import lombok.Getter;

@Getter
public class RouterNode extends AbstractNode {
    private final int routingCapacity;
    private int currentRoutes;

    public RouterNode(String id, int routingCapacity) {
        super(id, NodeType.ROUTER);
        this.routingCapacity = routingCapacity;
        this.currentRoutes = 0;
    }

    public boolean canAddRoute() {
        return currentRoutes < routingCapacity;
    }
}
