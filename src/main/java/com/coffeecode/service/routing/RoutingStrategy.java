package com.coffeecode.service.routing;

import java.util.List;

import com.coffeecode.domain.node.base.NetNode;

public interface RoutingStrategy {
    List<NetNode> findPath(NetNode source, NetNode destination);

    double calculatePathCost(List<NetNode> path);

    boolean isValidPath(List<NetNode> path);

    String getAlgorithmName();
}
