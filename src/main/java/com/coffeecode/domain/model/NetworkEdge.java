package com.coffeecode.domain.model;

import java.util.concurrent.atomic.AtomicBoolean;

import com.coffeecode.domain.common.NetID;

import lombok.Getter;

@Getter
public class NetworkEdge {
    private final NetID identity;
    private final NetworkNode source;
    private final NetworkNode destination;
    private final EdgeProperties properties;
    private final AtomicBoolean active;

    private NetworkEdge(NetworkNode source, NetworkNode destination, EdgeProperties properties) {
        this.identity = NetID.create("edge");
        this.source = source;
        this.destination = destination;
        this.properties = properties;
        this.active = new AtomicBoolean(true);
    }

    public static NetworkEdge create(NetworkNode source, NetworkNode destination, EdgeProperties properties) {
        return new NetworkEdge(source, destination, properties);
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean state) {
        active.set(state);
    }

    public double getWeight() {
        return properties.calculateWeight();
    }
}
