package com.coffeecode.domain.entities.node.base;

import java.util.List;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.entities.edge.Edge;

public interface Node {
    /**
     * Gets the identity of this node
     *
     * @return Identity containing id, name and description
     */
    Identity getIdentity();

    /**
     * Gets the type of this node
     *
     * @return NodeType enum value
     */
    NodeType getType();

    /**
     * Checks if node is active
     *
     * @return true if active, false otherwise
     */
    boolean isActive();

    /**
     * Sets node active status
     *
     * @param active status to set
     */
    void setActive(boolean active);

    /**
     * Gets all edges connected to this node
     *
     * @return Unmodifiable List of edges
     */
    List<Edge> getConnections();

    /**
     * Adds new connection to this node
     *
     * @param edge Edge to add
     * @throws IllegalArgumentException if edge is invalid
     */
    void addConnection(Edge edge);

    /**
     * Removes connection from this node
     *
     * @param edge Edge to remove
     */
    void removeConnection(Edge edge);
}
