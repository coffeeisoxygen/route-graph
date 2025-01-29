package com.coffeecode.domain.connection;

import java.util.List;
import java.util.Optional;

import com.coffeecode.domain.model.NetworkEdge;
import com.coffeecode.domain.model.NetworkNode;

public interface ConnectionManager {
    List<NetworkEdge> getConnections();

    void addConnection(NetworkEdge edge);

    void removeConnection(NetworkEdge edge);

    boolean hasConnection(NetworkEdge edge);

    int getConnectionCount();

    Optional<NetworkEdge> findConnection(NetworkNode target);

    boolean isConnectedTo(NetworkNode target);
}
