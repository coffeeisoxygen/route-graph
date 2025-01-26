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
    public FlowResult calculateFlow(Pipe pipe, double inputPressure) {
        // Validate input pressure
        if (inputPressure <= 0) {
            return calculateWithDefaults(pipe);
        }

        // Extract pipe properties
        double diameter = pipe.getDiameter();
        double roughness = pipe.getRoughness();
        double length = pipe.getLength().getMeters();

        // Calculate velocity, flow rate, head loss, and output pressure
        double velocity = calculateVelocity(inputPressure);
        double flowRate = calculateFlowRate(diameter, velocity);
        double headLoss = calculateHeadLoss(length, diameter, roughness, flowRate);
        double pressureOut = calculatePressureOut(inputPressure, headLoss);

        // Log calculations for debugging
        log.debug("Calculated Flow Rate: {} m³/s, Velocity: {} m/s, Head Loss: {} m, Output Pressure: {} Pa",
                flowRate, velocity, headLoss, pressureOut);

        return FlowResult.builder()
                .flowRate(flowRate)
                .pressureOut(pressureOut)
                .velocityOut(velocity)
                .headLoss(headLoss)
                .build();
    }

    private FlowResult calculateWithDefaults(Pipe pipe) {
        // Use default simulation parameters when no pressure is provided
        double velocity = SimulationDefaults.FLOW_VELOCITY;
        double diameter = pipe.getDiameter();
        double flowRate = calculateFlowRate(diameter, velocity);

        // Log default flow calculations
        log.debug("Using default velocity: {} m/s for pipe diameter: {} m", velocity, diameter);

        return FlowResult.builder()
                .flowRate(flowRate)
                .pressureOut(PhysicalConstants.ATMOSPHERIC_PRESSURE)
                .velocityOut(velocity)
                .headLoss(0.0)
                .build();
    }

    private double calculateFlowRate(double diameter, double velocity) {
        // Calculate flow rate based on pipe diameter and velocity
        double area = Math.PI * Math.pow(diameter / 2, 2);
        return area * velocity;
    }

    private double calculateVelocity(double inputPressure) {
        // Calculate velocity using simplified Bernoulli equation (v = sqrt(2 * P / ρ))
        return Math.sqrt((2 * inputPressure) / PhysicalConstants.WATER_DENSITY);
    }

    private double calculateHeadLoss(double length, double diameter, double roughness, double flowRate) {
        // Calculate the velocity from flow rate and pipe area
        double area = Math.PI * Math.pow(diameter / 2, 2);
        double velocity = flowRate / area;

        // Calculate Reynolds number
        double reynoldsNumber = (velocity * diameter) / PhysicalConstants.KINEMATIC_VISCOSITY;

        // Calculate friction factor based on flow type (Laminar or Turbulent)
        double frictionFactor;
        if (reynoldsNumber < 2000) {
            // Laminar flow: Use the formula f = 64 / Re
            frictionFactor = 64.0 / reynoldsNumber;
        } else {
            // Turbulent flow: Use Swamee-Jain equation for friction factor
            frictionFactor = calculateFrictionFactorSwameeJain(reynoldsNumber, diameter, roughness);
        }

        // Darcy-Weisbach head loss formula
        return frictionFactor * (length / diameter) * Math.pow(velocity, 2) / (2 * PhysicalConstants.GRAVITY);
    }

    /**
     * Calculates friction factor using the Swamee-Jain equation for turbulent
     * flow.
     *
     * @param reynoldsNumber Reynolds number
     * @param diameter Pipe diameter
     * @param roughness Pipe roughness
     * @return Friction factor for turbulent flow
     */
    private double calculateFrictionFactorSwameeJain(double reynoldsNumber, double diameter, double roughness) {
        // Swamee-Jain approximation for friction factor
        return 0.25 / Math.pow(
                Math.log10((roughness / diameter) / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9)),
                2);
    }

    private double calculatePressureOut(double inputPressure, double headLoss) {
        // Calculate the output pressure by subtracting head loss from input pressure
        double pressureLoss = headLoss * PhysicalConstants.WATER_DENSITY * PhysicalConstants.GRAVITY;
        double pressureOut = inputPressure - pressureLoss;

        // Ensure pressureOut is not less than atmospheric pressure
        return Math.max(pressureOut, PhysicalConstants.ATMOSPHERIC_PRESSURE);
    }
}
