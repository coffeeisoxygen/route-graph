package com.coffeecode.validation.validators;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HydraulicValidator {

    public static void validateHydraulicParameters(double length, double diameter,
            double velocity) {
        if (length <= 0) {
            throw new ValidationException("Pipe length must be positive");
        }
        if (diameter <= 0) {
            throw new ValidationException("Pipe diameter must be positive");
        }
        if (velocity < 0) {
            throw new ValidationException("Flow velocity cannot be negative");
        }
    }

    public static void validateReynoldsNumber(double reynoldsNumber) {
        if (reynoldsNumber <= 0) {
            throw new ValidationException("Reynolds number must be positive");
        }
        if (reynoldsNumber > 1e8) {
            throw new ValidationException("Reynolds number exceeds maximum limit");
        }
    }

    private HydraulicValidator() {

    }

    public static void validatePositiveValue(String string, double flowRate) {
        if (flowRate <= 0) {
            throw new ValidationException(string + " must be positive");
        }
    }
}
