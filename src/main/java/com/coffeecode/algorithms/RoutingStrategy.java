package com.coffeecode.algorithms;

import java.util.List;

import com.coffeecode.core.Node;

public interface RoutingStrategy {
    List<Node> findPath(Node source, Node destination);

    double calculatePathCost(List<Node> path);

    boolean isValidPath(List<Node> path);

    String getAlgorithmName();
}
