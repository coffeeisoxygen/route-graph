package com.coffeecode.service.flow.calculation;

import com.coffeecode.domain.constants.MaterialConstants;
import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HydraulicValidator {

    private static final double MAX_REYNOLDS = 1e8;
    private static final double MIN_FRICTION = 0.001;
    private static final double MAX_FRICTION = 0.1;
    private static final double MAX_VELOCITY = 10.0; // m/s

    private HydraulicValidator() {
    }

    public static void validateHydraulicParameters(double length, double diameter, double velocity) {
        validatePositiveValue("Pipe length", length);
        validateDiameter(diameter);
        validateVelocity(velocity);
    }

    public static void validatePressure(double pressure) {
        validatePositiveValue("Pressure", pressure);
        if (pressure > OperationalLimits.Pressure.MAX_SERVICE) {
            throw new ValidationException("Pressure exceeds maximum service limit");
        }
    }

    public static void validateHeadLoss(double headLoss) {
        validatePositiveValue("Head loss", headLoss);
    }

    public static void validateDiameter(double diameter) {
        validatePositiveValue("Diameter", diameter);
        if (diameter < MaterialConstants.PipeSizes.MIN || diameter > MaterialConstants.PipeSizes.MAX) {
            throw new ValidationException(String.format("Diameter must be between %.3f and %.3f meters",
                    MaterialConstants.PipeSizes.MIN, MaterialConstants.PipeSizes.MAX));
        }
    }

    public static void validateRoughness(double roughness) {
        validatePositiveValue("Roughness", roughness);
        if (roughness < MaterialConstants.Roughness.MIN || roughness > MaterialConstants.Roughness.MAX) {
            throw new ValidationException(String.format("Roughness must be between %.6f and %.6f meters",
                    MaterialConstants.Roughness.MIN, MaterialConstants.Roughness.MAX));
        }
    }

    public static void validateFrictionFactor(double frictionFactor) {
        validatePositiveValue("Friction factor", frictionFactor);
        if (frictionFactor < MIN_FRICTION || frictionFactor > MAX_FRICTION) {
            throw new ValidationException(String.format("Friction factor must be between %.3f and %.3f",
                    MIN_FRICTION, MAX_FRICTION));
        }
    }

    public static void validateVelocity(double velocity) {
        if (velocity < 0) {
            throw new ValidationException("Velocity cannot be negative");
        }
        if (velocity > MAX_VELOCITY) {
            throw new ValidationException(String.format("Velocity cannot exceed %.1f m/s", MAX_VELOCITY));
        }
    }

    public static void validateReynoldsNumber(double reynoldsNumber) {
        validatePositiveValue("Reynolds number", reynoldsNumber);
        if (reynoldsNumber > MAX_REYNOLDS) {
            throw new ValidationException("Reynolds number exceeds maximum limit");
        }
    }

    public static void validatePositiveValue(String name, double value) {
        if (value <= 0) {
            throw new ValidationException(name + " must be positive");
        }
    }
}
