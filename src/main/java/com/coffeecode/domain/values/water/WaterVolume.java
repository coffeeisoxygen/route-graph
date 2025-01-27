package com.coffeecode.domain.values.water;

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
public class WaterVolume {

    private final double cubicMeters;

    private WaterVolume(double cubicMeters) {
        validateCubicMeters(cubicMeters);
        this.cubicMeters = cubicMeters;
    }

    public static WaterVolume of(double cubicMeters) {
        return new WaterVolume(cubicMeters);
    }

    private static void validateCubicMeters(double cubicMeters) {
        if (cubicMeters < 0) {
            throw new ValidationException("Volume cannot be negative!");
        }
    }
}
