package com.coffeecode.domain.values.location;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

@Getter
@ToString
@EqualsAndHashCode
@Value
public class Elevation {

    private final double value; // meters above sea level

    private Elevation(Double value) {
        this.value = validateAndGetValue(value);
    }

    public static Elevation of(Double value) {
        return new Elevation(value);
    }

    private double validateAndGetValue(Double value) {
        if (value == null) {
            return OperationalLimits.ElevationLimits.DEFAULT;
        }
        if (value < OperationalLimits.ElevationLimits.MIN
                || value > OperationalLimits.ElevationLimits.MAX) {
            throw new ValidationException(
                    String.format("Elevation must be between %.2f and %.2f meters",
                            OperationalLimits.ElevationLimits.MIN,
                            OperationalLimits.ElevationLimits.MAX));
        }
        return value;
    }
}
