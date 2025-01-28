package com.coffeecode.network.calculator.flow;

import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadLoss;
import com.coffeecode.network.calculator.physics.properties.PressureDrop;

import lombok.Value;

/**
 * Represents flow distribution in network segment.
 * Handles flow rates and pressure drops between nodes.
 */
@Value
public class NetworkFlow {
    FlowRate flowRate;
    PressureDrop pressureDrop;
    boolean isValid;

    /**
     * Calculate network flow between two points
     *
     * @param initialPressure starting pressure (Pa)
     * @param flowRate        desired flow rate (m³/s)
     * @param headLoss        friction head loss (m)
     * @param elevationDiff   elevation difference (m)
     * @return NetworkFlow instance
     */
    public static NetworkFlow calculate(
            double initialPressure,
            FlowRate flowRate,
            HeadLoss headLoss,
            double elevationDiff) {

        PressureDrop pressureDrop = PressureDrop.calculate(headLoss, elevationDiff);
        boolean isValid = (initialPressure - pressureDrop.getValue()) > 0;

        return new NetworkFlow(flowRate, pressureDrop, isValid);
    }
}
