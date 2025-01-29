package com.coffeecode.domain.topology;

import java.util.List;
import java.util.Map;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;

public interface NetworkTopology {
    Map<String, Node> getNodes();

    List<Edge> getEdges();

    void addNode(Node node);

    void addEdge(Edge edge);

    boolean isConnected(String sourceId, String destId);
}
