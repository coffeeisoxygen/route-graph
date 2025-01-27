// // package com.coffeecode.service.flow;

// // import org.springframework.stereotype.Service;

// // import com.coffeecode.domain.constants.PhysicalConstants;
// // import com.coffeecode.domain.constants.SimulationDefaults;
// // import com.coffeecode.domain.entities.Pipe;
// // import com.coffeecode.service.flow.calculation.HeadLossCalculator;
// // import com.coffeecode.service.flow.calculation.PressureCalculator;
// // import com.coffeecode.service.flow.calculation.VelocityCalculator;
// // import com.coffeecode.validation.exceptions.ValidationException;
// // import com.coffeecode.validation.validators.FlowValidator;

// // import lombok.RequiredArgsConstructor;
// // import lombok.extern.slf4j.Slf4j;

// // /**
// // * Implementation of FlowCalculationService for water distribution network.
// // Uses
// // * hydraulic formulas to calculate flow parameters in pipes.
// // */

// import org.springframework.stereotype.Service;

// import com.coffeecode.domain.constants.PhysicalConstants;
// import com.coffeecode.domain.constants.SimulationDefaults;
// import com.coffeecode.service.flow.calculation.HeadLossCalculator;
// import com.coffeecode.validation.exceptions.ValidationException;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class FlowCalculationServiceImpl implements FlowCalculationService {

// private final HeadLossCalculator headLossCalculator;
// private final PressureCalculator pressureCalculator;
// private final VelocityCalculator velocityCalculator;

// @Override
// public FlowResult calculateFlow(Pipe pipe, double pressureIn) {
// FlowValidator.validatePipe(pipe);
// FlowValidator.validatePressure(pressureIn);

// if (pressureIn <= 0) {
// return calculateWithDefaults(pipe);
// }

// try {
// return calculateFlowParameters(pipe, pressureIn);
// } catch (Exception e) {
// log.error("Flow calculation failed: {}", e.getMessage());
// throw new ValidationException("Flow calculation failed: " + e.getMessage());
// }
// }

// private FlowResult calculateFlowParameters(Pipe pipe, double pressureIn) {
// double diameter = pipe.getProperties().getDiameter();
// double roughness = pipe.getProperties().getRoughness();
// double length = pipe.getProperties().getLength().getValue();

// // Calculate velocity and flow rate
// double velocity = velocityCalculator.calculate(pressureIn);
// double flowRate = calculateFlowRate(diameter, velocity);

// // Calculate Reynolds number and friction
// double reynolds = velocityCalculator.calculateWithReynolds(velocity,
// diameter);
// double friction = headLossCalculator.calculateFrictionFactor(reynolds,
// diameter, roughness);

// // Calculate head loss and pressure out
// double headLoss = headLossCalculator.calculate(length, diameter, friction,
// velocity);
// double pressureOut = pressureCalculator.calculatePressureOut(pressureIn,
// headLoss);

// return FlowResult.builder()
// .flowRate(flowRate)
// .pressureOut(pressureOut)
// .velocityOut(velocity)
// .headLoss(headLoss)
// .build();
// }

// private FlowResult calculateWithDefaults(Pipe pipe) {
// double velocity = SimulationDefaults.FLOW_VELOCITY;
// double diameter = pipe.getProperties().getDiameter();
// double flowRate = calculateFlowRate(diameter, velocity);

// log.debug("Using default velocity: {} m/s for pipe diameter: {} m",
// velocity, diameter);

// return FlowResult.builder()
// .flowRate(flowRate)
// .pressureOut(PhysicalConstants.Environment.ATMOSPHERIC_PRESSURE)
// .velocityOut(velocity)
// .headLoss(0.0)
// .build();
// }

// private double calculateFlowRate(double diameter, double velocity) {
// double area = Math.PI * Math.pow(diameter / 2, 2);
// return area * velocity;
// }
// }
