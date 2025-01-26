package com.coffeecode.logic.flow;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.domain.entity.Pipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * FlowCalculationServiceImpl provides the implementation for calculating flow
 * parameters (flow rate, pressure, velocity, head loss) for a pipe network.
 */
@Slf4j
@RequiredArgsConstructor
public class FlowCalculationServiceImpl implements FlowCalculationService {

    @Override
    public FlowResult calculateFlow(Pipe pipe, double pressureIn) {
        double diameter = pipe.getDiameter();
        double roughness = pipe.getRoughness();
        double length = pipe.getLength().getMeters();

        // Use defaults if pressure is zero
        if (pressureIn <= 0) {
            return calculateWithDefaults(pipe);
        }

        double velocity = calculateVelocity(pressureIn, diameter);
        double flowRate = calculateFlowRate(diameter, velocity);
        double headLoss = calculateHeadLoss(length, diameter, roughness, flowRate);
        double pressureOut = calculatePressureOut(pressureIn, headLoss);

        return FlowResult.builder()
                .flowRate(flowRate)
                .pressureOut(pressureOut)
                .velocityOut(velocity)
                .headLoss(headLoss)
                .build();
    }

    private FlowResult calculateWithDefaults(Pipe pipe) {
        double velocity = SimulationDefaults.FLOW_VELOCITY;
        double diameter = pipe.getDiameter();
        double flowRate = calculateFlowRate(diameter, velocity);

        return FlowResult.builder()
                .flowRate(flowRate)
                .pressureOut(PhysicalConstants.ATMOSPHERIC_PRESSURE)
                .velocityOut(velocity)
                .headLoss(0.0)
                .build();
    }

    private double calculateFlowRate(double diameter, double velocity) {
        return Math.PI * Math.pow(diameter / 2, 2) * velocity;
    }

    private double calculateVelocity(double pressureIn, double diameter) {
        return Math.sqrt(2 * pressureIn / (PhysicalConstants.WATER_DENSITY));
    }

    private double calculateHeadLoss(double length, double diameter,
            double roughness, double flowRate) {
        return (8 * roughness * length * Math.pow(flowRate, 2))
                / (Math.pow(Math.PI, 2) * Math.pow(diameter, 5)
                * PhysicalConstants.GRAVITY);
    }

    private double calculatePressureOut(double pressureIn, double headLoss) {
        return Math.max(
                pressureIn - (headLoss * PhysicalConstants.WATER_DENSITY
                * PhysicalConstants.GRAVITY),
                PhysicalConstants.ATMOSPHERIC_PRESSURE
        );
    }
}
