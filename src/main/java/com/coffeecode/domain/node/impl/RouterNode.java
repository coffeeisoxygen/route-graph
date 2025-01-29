package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RouterNode implements NetworkNode {
    private final NetworkIdentity identity;
    private final NodeProperties properties;
    private boolean active;
    private final RoutingTable routingTable;

    @Builder
    public RouterNode(NetworkIdentity identity, NodeProperties properties) {
        this.identity = identity;
        this.properties = properties;
        this.active = true;
        this.routingTable = new RoutingTable();
    }

    @Override
    public boolean canAcceptConnection() {
        return active && getCurrentConnections() < properties.getMaxConnections();
    }

    @Override
    public boolean canInitiateConnection() {
        return active;
    }
}
