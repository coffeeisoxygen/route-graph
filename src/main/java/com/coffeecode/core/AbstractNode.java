package com.coffeecode.core;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class AbstractNode implements Node {
    @NonNull
    private final String id;
    private final List<Edge> edges;
    private boolean active;
    private final NodeType type;

    protected AbstractNode(String id, NodeType type) {
        this.id = id;
        this.type = type;
        this.edges = new ArrayList<>();
        this.active = true;
    }

    @Override
    public void addEdge(Edge edge) {
        if (!edge.isValid()) {
            throw new IllegalArgumentException("Invalid edge configuration");
        }
        edges.add(edge);
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
