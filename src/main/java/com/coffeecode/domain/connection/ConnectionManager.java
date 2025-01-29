package com.coffeecode.domain.connection;

import java.util.List;
import java.util.Optional;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

public interface ConnectionManager {
    List<NetEdge> getConnections();

    void addConnection(NetEdge edge);

    void removeConnection(NetEdge edge);

    boolean hasConnection(NetEdge edge);

    int getConnectionCount();

    Optional<NetEdge> findConnection(NetNode target);

    boolean isConnectedTo(NetNode target);

    void validateMaxConnections(int maxConnections);
}
