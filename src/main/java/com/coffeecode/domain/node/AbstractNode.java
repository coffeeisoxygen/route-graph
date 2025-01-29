package com.coffeecode.domain.node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.coffeecode.domain.edge.Edge;

import lombok.Getter;

/**
 * Base implementation of Node interface providing common functionality
 * for all node types in the network.
 */
@Getter
public abstract class AbstractNode implements Node {
    private final UUID id;
    private final List<Edge> edges;
    private boolean active;
    private final NodeType type;

    /**
     * Creates a new node with specified type
     *
     * @param type NodeType of this node
     */
    protected AbstractNode(NodeType type) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.edges = new ArrayList<>();
        this.active = true; // default values
    }

    /**
     * Creates a node with a specific UUID (for batch creation)
     *
     * @param id   Predefined UUID
     * @param type NodeType of this node
     */
    protected AbstractNode(UUID id, NodeType type) {
        this.id = id;
        this.type = type;
        this.edges = new ArrayList<>();
        this.active = true;
    }

    @Override
    public String getIdString() {
        return id.toString();
    }

    @Override
    public void addEdge(Edge edge) {
        validateEdge(edge);
        edges.add(edge);
    }

    /**
     * Validates edge before adding
     *
     * @param edge Edge to validate
     * @throws IllegalArgumentException if edge is invalid
     */
    protected void validateEdge(Edge edge) {
        if (!edge.isValid()) {
            throw new IllegalArgumentException(
                    "Invalid edge configuration between " +
                            edge.getSource().getIdString() + " and " +
                            edge.getDestination().getIdString());
        }
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
