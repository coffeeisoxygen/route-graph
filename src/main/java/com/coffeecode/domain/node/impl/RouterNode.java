package com.coffeecode.domain.node.impl;

import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.Positive;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.node.base.AbstractNetNode;

import lombok.Getter;

@Component
@Scope("prototype")
@Getter
public class RouterNode extends AbstractNetNode {
    @Positive
    private final int routingCapacity;
    @Positive
    private final double bufferSize;
    private final boolean supportsQos;
    private final AtomicInteger currentRoutes;

    public RouterNode(int routingCapacity, double bufferSize, boolean supportsQos,
            Integer maxConnections, ConnectionManager connectionManager) {
        super(NetNodeType.ROUTER, maxConnections, connectionManager);
        this.routingCapacity = routingCapacity;
        this.bufferSize = bufferSize;
        this.supportsQos = supportsQos;
        this.currentRoutes = new AtomicInteger(0);
    }

    @Override
    public boolean isValid() {
        return super.isValid() &&
                routingCapacity > 0 && bufferSize > 0;
    }
}
