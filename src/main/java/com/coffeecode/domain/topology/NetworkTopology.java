package com.coffeecode.domain.topology;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

public interface NetworkTopology {
    // Core operations
    Map<String, NetNode> getNodes();

    List<NetEdge> getEdges();

    void addNode(NetNode node);

    void addEdge(NetEdge edge);

    // Node operations
    Optional<NetNode> findNode(String nodeId);

    boolean removeNode(String nodeId);

    // Edge operations
    Optional<NetEdge> findEdge(String sourceId, String destId);

    boolean removeEdge(NetEdge edge);

    // Topology analysis
    boolean isConnected(String sourceId, String destId);

    List<NetEdge> findPath(String sourceId, String destId);

    int getNodeCount();

    int getEdgeCount();

    // Network state
    boolean isActive();

    void setActive(boolean active);

    double getNetworkLoad();
}
