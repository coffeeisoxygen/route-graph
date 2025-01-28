package com.coffeecode.domain.node;

import java.util.List;

import com.coffeecode.domain.edge.Edge;

public interface Node {
    String getId();

    boolean isActive();

    void setActive(boolean active);

    List<Edge> getEdges();

    void addEdge(Edge edge);

    NodeType getType();

    enum NodeType {
        ROUTER,
        CLIENT,
        SERVER
    }
}
