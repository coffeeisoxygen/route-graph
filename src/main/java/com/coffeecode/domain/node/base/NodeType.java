package com.coffeecode.domain.node.base;

import lombok.Getter;

/**
 * Represents node types in the network with associated metadata.
 */
@Getter
public enum NodeType {
    ROUTER("Router", "Network routing device", 1000),
    CLIENT("Client", "End-user device", 100),
    SERVER("Server", "Service provider", 500);

    private final String displayName;
    private final String description;
    private final int defaultCapacity;

    NodeType(String displayName, String description, int defaultCapacity) {
        this.displayName = displayName;
        this.description = description;
        this.defaultCapacity = defaultCapacity;
    }

    /**
     * Returns prefix for node naming convention
     *
     * @return lowercase name for prefix
     */
    public String getNamePrefix() {
        return this.name().toLowerCase();
    }

    /**
     * Checks if node type can initiate connections
     *
     * @return true if can initiate, false otherwise
     */
    public boolean canInitiateConnection() {
        return this != ROUTER;
    }

}
