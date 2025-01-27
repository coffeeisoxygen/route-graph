package com.coffeecode.domain.values.water;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

/**
 * Represents daily water demand in cubic meters per day (m³/day).
 *
 * <p>
 * This immutable value object ensures that water demand values are within
 * valid operational ranges defined in OperationalLimits.
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

    private void validateDemand(double value) {
        if (value < OperationalLimits.Customer.MIN_DEMAND ||
                value > OperationalLimits.Customer.MAX_DEMAND) {
            throw ValidationException.invalidRange("Daily demand",
                    OperationalLimits.Customer.MIN_DEMAND,
                    OperationalLimits.Customer.MAX_DEMAND);
        }
    }
}
