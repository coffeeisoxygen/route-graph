package com.coffeecode.domain.node.router.component;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RouterComponents implements RouterComponent {
    private final ConnectionManager connections;
    private final RoutingTable routes;
    private final MetricsCollector metrics;

    public static RouterComponents create() {
        return RouterComponents.builder()
                .connections(new ConnectionManager())
                .routes(new RoutingTable())
                .metrics(new MetricsCollector())
                .build();
    }

    @Override
    public void initialize() {
        connections.initialize();
        routes.initialize();
        metrics.initialize();
    }

    @Override
    public void clear() {
        connections.clear();
        routes.clear();
        metrics.clear();
    }

    @Override
    public boolean isActive() {
        return connections.isActive() &&
                routes.isActive() &&
                metrics.isActive();
    }
}
