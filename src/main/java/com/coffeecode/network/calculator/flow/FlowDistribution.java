package com.coffeecode.network.calculator.flow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterNodes;
import com.coffeecode.network.nodes.WaterSource;

import lombok.Value;

@Value
public class FlowDistribution {
    Map<WaterNodes, Set<NetworkFlow>> flowMap;
    MassBalance massBalance;
    boolean isValid;

    public static FlowDistribution calculate(
            Set<WaterSource> sources,
            Set<WaterDemand> demands,
            Map<WaterNodes, Map<WaterNodes, NetworkFlow>> connections) {

        if (connections == null) {
            throw new IllegalArgumentException("Connections cannot be null");
        }

        FlowRate totalSupply = calculateTotalSupply(sources);
        FlowRate totalDemand = calculateTotalDemand(demands);

        MassBalance massBalance = MassBalance.calculate(totalSupply, totalDemand);
        System.out.println("Total Supply: " + totalSupply.getValue());
        System.out.println("Total Demand: " + totalDemand.getValue());
        System.out.println("Mass Balance isBalanced: " + massBalance.isBalanced());
        Map<WaterNodes, Set<NetworkFlow>> flowMap = buildFlowMap(connections);
        boolean isValid = validateFlows(flowMap, massBalance);

        return new FlowDistribution(flowMap, massBalance, isValid);
    }

    private static FlowRate calculateTotalSupply(Set<WaterSource> sources) {
        double total = sources.stream()
                .mapToDouble(s -> s.getFlowRate().getValue())
                .sum();
        return FlowRate.of(total);
    }

    private static FlowRate calculateTotalDemand(Set<WaterDemand> demands) {
        double total = demands.stream()
                .mapToDouble(d -> d.getDemand().getValue())
                .sum();
        return FlowRate.of(total);
    }

    private static Map<WaterNodes, Set<NetworkFlow>> buildFlowMap(
            Map<WaterNodes, Map<WaterNodes, NetworkFlow>> connections) {
        Map<WaterNodes, Set<NetworkFlow>> flowMap = new HashMap<>();

        connections.forEach((from, toMap) -> flowMap.computeIfAbsent(from, k -> new HashSet<>())
                .addAll(toMap.values()));

        return flowMap;
    }

    private static boolean validateFlows(
            Map<WaterNodes, Set<NetworkFlow>> flowMap,
            MassBalance massBalance) {
        return massBalance.isBalanced() &&
                flowMap.values().stream()
                        .flatMap(Set::stream)
                        .allMatch(NetworkFlow::isValid);
    }
}
