package com.coffeecode.domain.node.impl;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Positive;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.AbstractNode;
import com.coffeecode.domain.node.base.NodeType;

import lombok.Getter;

/**
 * Represents a client node in the network that can send and receive data.
 * Handles data transmission rate limiting and usage tracking.
 */
@Getter
public class ClientNode extends AbstractNode {
    @Positive
    private final double dataRate;
    private double currentUsage;

    /**
     * Creates a new client node with specified data rate
     *
     * @param id       Unique identifier for the node
     * @param dataRate Maximum data transmission rate in bytes/second
     * @throws IllegalArgumentException if dataRate is negative
     */
    public ClientNode(String id, @Positive double dataRate) {
        super(UUID.fromString(id), NodeType.CLIENT);
        if (dataRate <= 0) {
            throw new IllegalArgumentException("Data rate must be positive");
        }
        this.dataRate = dataRate;
        this.currentUsage = 0;
    }

    /**
     * Checks if the node can transmit data of given size
     *
     * @param dataSize Size of data to transmit in bytes
     * @return true if transmission is possible, false otherwise
     */
    public synchronized boolean canTransmit(double dataSize) {
        return (currentUsage + dataSize) <= dataRate;
    }

    /**
     * Records data transmission
     *
     * @param dataSize Size of transmitted data
     * @throws IllegalArgumentException if transmission would exceed capacity
     */
    public synchronized void recordTransmission(double dataSize) {
        if (!canTransmit(dataSize)) {
            throw new IllegalArgumentException("Transmission would exceed capacity");
        }
        currentUsage += dataSize;
    }

    /**
     * Resets the current usage counter
     */
    public synchronized void resetUsage() {
        currentUsage = 0;
    }

    @Override
    public void setActive(boolean active) {
        if (active) {
            resetUsage();
        }
        super.setActive(active);
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
