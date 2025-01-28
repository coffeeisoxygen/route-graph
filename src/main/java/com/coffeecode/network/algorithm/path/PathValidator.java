package com.coffeecode.network.algorithm.path;

import com.coffeecode.network.core.WaterNetwork;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterSource;

public class PathValidator {
    private PathValidator() {
        // private constructor to prevent instantiation
    }

    public void validateNodes(WaterNetwork network, WaterSource source, WaterDemand target) {
        if (!network.getSources().contains(source)) {
            throw new IllegalArgumentException("Source node not in network");
        }
        if (!network.getDemands().contains(target)) {
            throw new IllegalArgumentException("Target node not in network");
        }
    }
}
