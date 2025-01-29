package com.coffeecode.domain.node.model;

import java.util.List;
import java.util.UUID;

import com.coffeecode.domain.edge.Edge;

/**
 * Core interface representing a node in the network topology.
 * Each node has a unique identifier, maintains a list of edges,
 * and can be activated or deactivated.
 */
public interface Node {
    /**
     * Gets the unique identifier of this node.
     *
     * @return UUID of the node
     */
    UUID getId();

    /**
     * Gets the string representation of node's UUID
     *
     * @return String representation of UUID
     */
    String getIdString();

    /**
     * Checks if the node is currently active
     *
     * @return true if node is active, false otherwise
     */
    boolean isActive();

    /**
     * Sets the active status of the node
     *
     * @param active the status to set
     */
    void setActive(boolean active);

    /**
     * Gets all edges connected to this node
     *
     * @return List of edges
     */
    List<Edge> getEdges();

    /**
     * Adds a new edge to this node
     *
     * @param edge the edge to add
     * @throws IllegalArgumentException if edge is invalid
     */
    void addEdge(Edge edge);

    /**
     * Gets the type of this node
     *
     * @return NodeType enum value
     */
    NodeType getType();

    /**
     * Processes a batch of edges
     *
     * @param edges List of edges to process
     */
    void batchProcess(List<Edge> edges);

    /**
     * Checks if the node is valid
     *
     * @return true if node is valid, false otherwise
     */
    boolean isValid();
}
