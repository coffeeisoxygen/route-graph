package com.coffeecode.algorithms;

import java.util.List;

import com.coffeecode.core.Node;

public interface RoutingStrategy {
    /**
     * Finds the shortest path between source and destination nodes
     *
     * @param source      Starting node
     * @param destination Target node
     * @return List of nodes representing the path, empty if no path exists
     */
    List<Node> findPath(Node source, Node destination);
}
