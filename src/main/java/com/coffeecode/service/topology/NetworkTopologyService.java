package com.coffeecode.service.topology;

import java.util.List;
import java.util.Optional;

import com.coffeecode.domain.node.base.NetNode;

public interface NetworkTopologyService {
    void addNode(NetNode node);

    void connect(String sourceId, String destId, double bandwidth, double latency);

    List<NetNode> getNeighbors(String nodeId);

    Optional<NetNode> getNode(String id);

    boolean isConnected(String sourceId, String destId);
}
