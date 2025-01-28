package com.coffeecode.network.calculator.flow;

import com.coffeecode.network.calculator.physics.properties.FlowRate;

import lombok.Value;

@Value
public class MassBalance {
    public static final double TOLERANCE = 1e-3; // 0.1% difference allowed

    FlowRate inFlow;
    FlowRate outFlow;
    boolean isBalanced;

    public static MassBalance calculate(FlowRate inFlow, FlowRate outFlow) {
        double diff = Math.abs(inFlow.getValue() - outFlow.getValue());
        double relDiff = diff / Math.max(inFlow.getValue(), outFlow.getValue());
        boolean isBalanced = relDiff < TOLERANCE;
        return new MassBalance(inFlow, outFlow, isBalanced);
    }
}
