package com.coffeecode.domain.connection;

import java.util.List;
import java.util.Optional;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;

public interface ConnectionManager {
    List<Edge> getConnections();

    void addConnection(Edge edge);

    void removeConnection(Edge edge);

    boolean hasConnection(Edge edge);

    int getConnectionCount();

    Optional<Edge> findConnection(Node target);

    boolean isConnectedTo(Node target);

    void validateMaxConnections(int maxConnections);
}
