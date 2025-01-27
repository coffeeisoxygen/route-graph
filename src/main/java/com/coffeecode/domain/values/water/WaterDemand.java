package com.coffeecode.domain.values.water;

import com.coffeecode.domain.constants.CustomerConstants;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

/**
 * Represents daily water demand in cubic meters per day (m³/day).
 *
 * <p>
 * This immutable value object ensures that water demand values are within
 * valid operational ranges defined in CustomerConstants.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * WaterDemand demand = WaterDemand.of(100.0); // 100 m³/day
 * double value = demand.value(); // Get demand value
 * </pre>
 */
@Value
public class WaterDemand {
    double value; // m³/day

    private WaterDemand(double value) {
        validateDemand(value);
        this.value = value;
    }

    /**
     * Creates a new WaterDemand instance
     *
     * @param value daily water demand in m³/day
     * @return new WaterDemand instance
     * @throws ValidationException if value is outside valid range
     */
    public static WaterDemand of(double value) {
        return new WaterDemand(value);
    }

    private static void validateDemand(double value) {
        if (value < CustomerConstants.MIN_DEMAND ||
                value > CustomerConstants.MAX_DEMAND) {
            throw new ValidationException(
                    String.format("Daily demand must be between %.3f and %.3f m³/day",
                            CustomerConstants.MIN_DEMAND,
                            CustomerConstants.MAX_DEMAND));
        }
    }
}
