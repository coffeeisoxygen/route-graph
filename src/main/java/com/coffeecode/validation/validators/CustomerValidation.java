package com.coffeecode.validation.validators;

import com.coffeecode.domain.values.WaterDemand;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CustomerValidation {

    private CustomerValidation() {
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Customer name cannot be empty");
        }
    }

    public static void validateWaterDemand(WaterDemand waterDemand) {
        if (waterDemand == null) {
            throw new ValidationException("Water demand cannot be null");
        }
    }
}
