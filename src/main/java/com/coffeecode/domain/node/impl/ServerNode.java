package com.coffeecode.domain.node.impl;

import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.NotNull;
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
public class ServerNode extends AbstractNetNode {
    @NotNull
    @Positive
    private final Integer capacity;
    @NotNull
    @Positive
    private final Double processingPower;
    private final AtomicInteger currentLoad;

    public ServerNode(Integer capacity, Double processingPower,
            Integer maxConnections, ConnectionManager connectionManager) {
        super(NetNodeType.SERVER, maxConnections, connectionManager);
        this.capacity = capacity;
        this.processingPower = processingPower;
        this.currentLoad = new AtomicInteger(0);
    }

    @Override
    public boolean isValid() {
        return super.isValid() &&
                capacity != null && capacity > 0 &&
                processingPower != null && processingPower > 0;
    }
}
