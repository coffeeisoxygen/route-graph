package com.coffeecode.domain.node.model;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.Positive;

import com.coffeecode.domain.edge.Edge;

import lombok.Getter;

/**
 * Represents a router node that handles packet routing in the network.
 * Manages routing capacity and current route count.
 */
@Getter
public class RouterNode extends AbstractNode {
    @Positive
    private final int routingCapacity;
    private final AtomicInteger currentRoutes;

    /**
     * Creates a new router node
     *
     * @param id              Unique identifier for the node
     * @param routingCapacity Maximum number of concurrent routes
     * @throws IllegalArgumentException if routingCapacity is negative
     */
    public RouterNode(String id, @Positive int routingCapacity) {
        super(UUID.fromString(id), NodeType.ROUTER);
        if (routingCapacity <= 0) {
            throw new IllegalArgumentException("Routing capacity must be positive");
        }
        this.routingCapacity = routingCapacity;
        this.currentRoutes = new AtomicInteger(0);
    }

    /**
     * Checks if router can handle additional routes
     *
     * @return true if new route can be added, false otherwise
     */
    public boolean canAddRoute() {
        return currentRoutes.get() < routingCapacity;
    }

    /**
     * Attempts to add a new route
     *
     * @return true if route was added, false if at capacity
     */
    public boolean addRoute() {
        return currentRoutes.get() < routingCapacity &&
                currentRoutes.incrementAndGet() <= routingCapacity;
    }

    /**
     * Removes a route from the router
     */
    public void removeRoute() {
        currentRoutes.updateAndGet(routes -> Math.max(0, routes - 1));
    }

    @Override
    public void batchProcess(List<Edge> edges) {
        edges.forEach(this::addEdge);
    }

    @Override
    public boolean isValid() {
        return getId() != null && getType() != null && getEdges() != null;
    }
}
