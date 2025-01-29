package com.coffeecode.domain.node.client.component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientComponents implements ClientComponent {
    private final ConnectionManager connections;
    private final RequestManager requests;

    public static ClientComponents create() {
        return ClientComponents.builder()
                .connections(new ConnectionManager())
                .requests(new RequestManager())
                .build();
    }

    @Override
    public void initialize() {
        connections.initialize();
        requests.initialize();
    }

    @Override
    public void clear() {
        connections.clear();
        requests.clear();
    }

    @Override
    public boolean isActive() {
        return connections.isActive() && requests.isActive();
    }
}
