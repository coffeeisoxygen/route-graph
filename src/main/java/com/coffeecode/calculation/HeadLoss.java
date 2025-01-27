package com.coffeecode.calculation;

import com.coffeecode.config.AppProperties;
import com.coffeecode.model.Pipe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeadLoss {
        private final AppProperties config = AppProperties.getInstance();

        public double calculateTotalHead(Pipe pipe) {
                double elevationLoss = pipe.getEnd().getElevation() -
                                pipe.getStart().getElevation();
                double frictionLoss = calculateFrictionLoss(pipe);

                return elevationLoss + frictionLoss;
        }

        private double calculateFrictionLoss(Pipe pipe) {
                double velocity = config.getDouble("water.velocity.default");
                double reynolds = (velocity * pipe.getDiameter()) /
                                config.getDouble("physical.kinematic.viscosity");
                double friction = calculateFrictionFactor(reynolds,
                                pipe.getRoughness(),
                                pipe.getDiameter());

                return friction * (pipe.getLength() / pipe.getDiameter()) *
                                Math.pow(velocity, 2) / (2 * config.getDouble("physical.gravity"));
        }

        private double calculateFrictionFactor(double reynolds,
                        double roughness,
                        double diameter) {
                double relativeRoughness = roughness / diameter;
                return 0.25 / Math.pow(
                                Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynolds, 0.9)), 2);
        }
}
