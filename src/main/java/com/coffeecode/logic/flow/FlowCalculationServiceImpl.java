package com.coffeecode.logic.flow;

import com.coffeecode.domain.constants.ElevationRange;
import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.domain.entity.Pipe;
import com.coffeecode.domain.objects.Distance;
import com.coffeecode.domain.objects.PipeProperties;
import com.coffeecode.domain.objects.Volume;

import lombok.RequiredArgsConstructor;

/**
 * FlowCalculationServiceImpl provides the implementation for calculating flow
 * parameters (flow rate, pressure, velocity, head loss) for a pipe network.
 */
@RequiredArgsConstructor
public class FlowCalculationServiceImpl implements FlowCalculationService {

    @Override
    public FlowResult calculateFlow(Pipe pipe, double pressureIn) {
        // Get pipe properties
        PipeProperties properties = pipe.getProperties();
        Distance length = properties.getLength();
        Volume capacity = properties.getCapacity();

        // Apply default values for missing parameters
        double diameter = properties.getDiameter();
        double roughness = properties.getRoughness();
        double velocity = SimulationDefaults.FLOW_VELOCITY;
        double pressure = pressureIn > 0 ? pressureIn : SimulationDefaults.PRESSURE_IN;

        // Ensure the elevation is within the valid range
        double elevation = SimulationDefaults.ELEVATION;
        if (elevation < ElevationRange.MIN.getValue() || elevation > ElevationRange.MAX.getValue()) {
            throw new IllegalArgumentException("Elevation value is out of range.");
        }

        // Calculate flow rate, pressure, velocity, and head loss
        double flowRate = calculateFlowRate(diameter, velocity);
        double pressureOut = calculatePressure(pressure, length.getValue(), roughness);
        double velocityOut = calculateVelocity(flowRate, diameter);
        double headLoss = calculateHeadLoss(length.getValue(), diameter, roughness, flowRate);

        return FlowResult.builder()
                .flowRate(flowRate)
                .pressureOut(pressureOut)
                .velocityOut(velocityOut)
                .headLoss(headLoss)
                .build();
    }

    private double calculateFlowRate(double diameter, double velocity) {
        // Example calculation (just a simple one for illustration)
        return Math.PI * Math.pow(diameter / 2, 2) * velocity;
    }

    private double calculatePressure(double pressureIn, double length, double roughness) {
        // Simplified pressure drop calculation (Darcy-Weisbach)
        return pressureIn - (length * roughness); // This is just an illustrative example
    }

    private double calculateVelocity(double flowRate, double diameter) {
        // Simplified velocity calculation
        return flowRate / (Math.PI * Math.pow(diameter / 2, 2));
    }

    private double calculateHeadLoss(double length, double diameter, double roughness, double flowRate) {
        // Simplified head loss calculation using Darcy-Weisbach equation
        return (length * roughness * Math.pow(flowRate, 2)) / Math.pow(diameter, 5);
    }
}
