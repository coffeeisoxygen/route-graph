package com.coffeecode.domain.values.water;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

/**
 * Immutable value object representing water flow rate.
 * Uses Lombok @Value for immutability and utility methods.
 */
@Value
public class WaterFlow {
    double value;  // flow rate in m³/s

    private WaterFlow(double value) {
        validate(value);
        this.value = value;
    }

    /**
     * Factory method to create a new WaterFlow instance
     * @param value flow rate in m³/s
     * @return new WaterFlow instance
     * @throws ValidationException if value is invalid
     */
    public static WaterFlow of(double value) {
        return new WaterFlow(value);
    }

    private void validate(double value) {
        if (value < 0) {
            throw new ValidationException("Flow rate cannot be negative");
        }
    }
}
