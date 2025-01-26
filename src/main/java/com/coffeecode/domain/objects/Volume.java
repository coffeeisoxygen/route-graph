package com.coffeecode.domain.objects;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a volume measurement in cubic meters.
 */
@Getter
@ToString(of = "cubicMeters")
@EqualsAndHashCode
public class Volume {

    private final double cubicMeters;

    private Volume(double cubicMeters) {
        validateCubicMeters(cubicMeters);
        this.cubicMeters = cubicMeters;
    }

    public static Volume of(double cubicMeters) {
        return new Volume(cubicMeters);
    }

    private static void validateCubicMeters(double cubicMeters) {
        if (cubicMeters < 0) {
            throw new ValidationException("Volume cannot be negative!");
        }
    }
}
