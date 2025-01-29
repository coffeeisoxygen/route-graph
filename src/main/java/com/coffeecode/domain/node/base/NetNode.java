package com.coffeecode.domain.node.base;

import java.util.List;

import com.coffeecode.domain.common.NetID;
import com.coffeecode.domain.edge.NetEdge;

public interface NetNode {
    /**
     * Gets the identity of this node
     *
     * @return Identity containing id, name and description
     */
    NetID getIdentity();

    /**
     * Gets the type of this node
     *
     * @return NodeType enum value
     */
    NetNodeType getType();

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
    List<NetEdge> getConnections();

    /**
     * Adds new connection to this node
     *
     * @param edge Edge to add
     * @throws IllegalArgumentException if edge is invalid
     */
    void addConnection(NetEdge edge);

    /**
     * Removes connection from this node
     *
     * @param edge Edge to remove
     */
    void removeConnection(NetEdge edge);

    /**
     * Gets the properties specific to this node type
     *
     * @return Node properties object
     */
    Object getProperties();
}
