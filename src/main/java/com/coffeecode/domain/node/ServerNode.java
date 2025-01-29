package com.coffeecode.domain.node;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.Positive;

import lombok.Getter;

/**
 * Represents a server node that processes requests and manages load.
 * Handles capacity management and processing capabilities.
 */
@Getter
public class ServerNode extends AbstractNode {
    @Positive
    private final int capacity;
    @Positive
    private final double processingPower;
    private final AtomicInteger currentLoad;

    /**
     * Creates a new server node
     *
     * @param id              Unique identifier for the node
     * @param capacity        Maximum number of concurrent requests
     * @param processingPower Processing capability in operations/second
     * @throws IllegalArgumentException if capacity or processingPower is invalid
     */
    public ServerNode(String id, @Positive int capacity, @Positive double processingPower) {
        super(UUID.fromString(id), NodeType.SERVER);
        if (capacity <= 0 || processingPower <= 0) {
            throw new IllegalArgumentException("Capacity and processing power must be positive");
        }
        this.capacity = capacity;
        this.processingPower = processingPower;
        this.currentLoad = new AtomicInteger(0);
    }

    /**
     * Checks if server can handle new requests
     *
     * @return true if new requests can be accepted, false otherwise
     */
    public boolean canHandleRequest() {
        return currentLoad.get() < capacity;
    }

    /**
     * Attempts to add a new request to the server
     *
     * @return true if request was accepted, false if at capacity
     */
    public boolean addRequest() {
        return currentLoad.get() < capacity &&
                currentLoad.incrementAndGet() <= capacity;
    }

    /**
     * Completes a request and reduces the load
     */
    public void completeRequest() {
        currentLoad.updateAndGet(load -> Math.max(0, load - 1));
    }

    /**
     * Gets the current load percentage
     *
     * @return Load percentage between 0 and 100
     */
    public double getLoadPercentage() {
        return (currentLoad.get() * 100.0) / capacity;
    }
}
