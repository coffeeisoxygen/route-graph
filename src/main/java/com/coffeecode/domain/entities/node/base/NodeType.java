package com.coffeecode.domain.entities.node.base;

import lombok.Getter;

/**
 * Represents the different types of nodes in the network.
 */
@Getter
public enum NodeType {
    ROUTER("Router", "Network routing device"),
    CLIENT("Client", "End-user device"),
    SERVER("Server", "Service provider");

    private final String displayName;
    private final String description;

    NodeType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Returns the prefix for node naming
     *
     * @return prefix string for node names
     */
    public String getNamePrefix() {
        return this.name().toLowerCase();
    }
}
