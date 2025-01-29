package com.coffeecode.domain.connection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectionCapability {
    private final boolean canInitiateConnection;
    private final int maxConnections;

    public static ConnectionCapability forRouter() {
        return ConnectionCapability.builder()
                .canInitiateConnection(false)
                .maxConnections(Integer.MAX_VALUE)
                .build();
    }

    public static ConnectionCapability forClient() {
        return ConnectionCapability.builder()
                .canInitiateConnection(true)
                .maxConnections(1)
                .build();
    }

    public static ConnectionCapability forServer() {
        return ConnectionCapability.builder()
                .canInitiateConnection(true)
                .maxConnections(100)
                .build();
    }
}
