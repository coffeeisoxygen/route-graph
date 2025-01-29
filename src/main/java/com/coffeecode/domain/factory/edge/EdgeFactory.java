package com.coffeecode.domain.factory.edge;

import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.edge.EdgeProperties;
import com.coffeecode.domain.entities.node.base.Node;

import java.util.List;

public interface EdgeFactory {
    Edge createEdge(Node source, Node target, EdgeProperties props);

    List<Edge> createBidirectional(Node nodeA, Node nodeB, EdgeProperties props);

    List<Edge> connectAll(List<Node> nodes, EdgeProperties props);
}
