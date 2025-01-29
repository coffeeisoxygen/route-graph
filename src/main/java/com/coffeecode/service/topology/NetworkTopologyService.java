package com.coffeecode.service.topology;

import java.util.List;
import java.util.Optional;

import com.coffeecode.domain.node.base.Node;

public interface NetworkTopologyService {
    void addNode(Node node);

    void connect(String sourceId, String destId, double bandwidth, double latency);

    List<Node> getNeighbors(String nodeId);

    Optional<Node> getNode(String id);

    boolean isConnected(String sourceId, String destId);
}
