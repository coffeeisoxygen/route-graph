package com.coffeecode.core;

import java.util.List;

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
