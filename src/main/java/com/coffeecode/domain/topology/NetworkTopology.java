package com.coffeecode.domain.topology;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;

public interface NetworkTopology {
    // Core operations
    Map<String, Node> getNodes();
    List<Edge> getEdges();
    void addNode(Node node);
    void addEdge(Edge edge);

    // Node operations
    Optional<Node> findNode(String nodeId);
    boolean removeNode(String nodeId);

    // Edge operations
    Optional<Edge> findEdge(String sourceId, String destId);
    boolean removeEdge(Edge edge);

    // Topology analysis
    boolean isConnected(String sourceId, String destId);
    List<Edge> findPath(String sourceId, String destId);
    int getNodeCount();
    int getEdgeCount();

    // Network state
    boolean isActive();
    void setActive(boolean active);
    double getNetworkLoad();
}
