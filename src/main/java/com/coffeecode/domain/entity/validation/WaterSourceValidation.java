package com.coffeecode.domain.entity.validation;

import com.coffeecode.domain.objects.Volume;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WaterSourceValidation {

    private WaterSourceValidation() {
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Source name cannot be empty");
        }
    }

    public static void validateCapacity(Volume capacity) {
        if (capacity == null) {
            throw new ValidationException("Capacity cannot be null");
        }
    }
}
