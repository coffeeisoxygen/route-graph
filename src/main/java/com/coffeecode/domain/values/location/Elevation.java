package com.coffeecode.domain.values.location;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Elevation {

    private final double value;  // meters above sea level

    private Elevation(Double value) {
        this.value = validateAndGetValue(value);
    }

    public static Elevation of(Double value) {
        return new Elevation(value);
    }

    private double validateAndGetValue(Double value) {
        if (value == null) {
            return OperationalLimits.Elevation.DEFAULT;
        }
        if (value < OperationalLimits.Elevation.MIN
                || value > OperationalLimits.Elevation.MAX) {
            throw new ValidationException(
                    String.format("Elevation must be between %.2f and %.2f meters",
                            OperationalLimits.Elevation.MIN,
                            OperationalLimits.Elevation.MAX));
        }
        return value;
    }
}
