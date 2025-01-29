package com.coffeecode.service.routing;

import java.util.List;

import com.coffeecode.domain.node.base.Node;

public interface RoutingStrategy {
    List<Node> findPath(Node source, Node destination);

    double calculatePathCost(List<Node> path);

    boolean isValidPath(List<Node> path);

    String getAlgorithmName();
}
