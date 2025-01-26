package com.coffeecode.logic.flow;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.domain.entity.Pipe;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of FlowCalculationService for water distribution network. Uses
 * hydraulic formulas to calculate flow parameters in pipes.
 */
@Slf4j
@RequiredArgsConstructor
public class FlowCalculationServiceImpl implements FlowCalculationService {

    @Override
    public FlowResult calculateFlow(Pipe pipe, double pressureIn) {
        validateInput(pipe, pressureIn);

        if (pressureIn <= 0) {
            log.debug("Using default calculations for zero/negative pressure");
            return calculateWithDefaults(pipe);
        }

        try {
            double diameter = pipe.getDiameter();
            double roughness = pipe.getRoughness();
            double length = pipe.getLength().getMeters();

            double velocity = calculateVelocity(pressureIn);
            double flowRate = calculateFlowRate(diameter, velocity);
            double reynoldsNumber = calculateReynoldsNumber(velocity, diameter);
            double frictionFactor = calculateFrictionFactorSwameeJain(reynoldsNumber, diameter, roughness);
            double headLoss = calculateHeadLoss(length, diameter, frictionFactor, velocity);
            double pressureOut = calculatePressureOut(pressureIn, headLoss);

            log.debug("Flow calculation completed: velocity={}, flowRate={}, headLoss={}",
                    velocity, flowRate, headLoss);

            return FlowResult.builder()
                    .flowRate(flowRate)
                    .pressureOut(pressureOut)
                    .velocityOut(velocity)
                    .headLoss(headLoss)
                    .build();
        } catch (Exception e) {
            log.error("Error calculating flow: {}", e.getMessage());
            throw new ValidationException("Flow calculation failed: " + e.getMessage());
        }
    }

    private void validateInput(Pipe pipe, double pressureIn) {
        if (pipe == null) {
            throw new ValidationException("Pipe cannot be null");
        }
        if (pressureIn < 0) {
            throw new ValidationException("Pressure cannot be negative");
        }
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

    private double calculateReynoldsNumber(double velocity, double diameter) {
        return (velocity * diameter) / PhysicalConstants.KINEMATIC_VISCOSITY;
    }

    private double calculateHeadLoss(double length, double diameter, double frictionFactor, double velocity) {
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
