package com.coffeecode.algorithms;

import com.coffeecode.core.Node;

public interface RoutingStrategy {
    /**
     * Determines the best path between two nodes in the network.
     *
     * @param source      the starting node
     * @param destination the target node
     * @return an array of nodes representing the best path
     */
    Node[] findBestPath(Node source, Node destination);
}
