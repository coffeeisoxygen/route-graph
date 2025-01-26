package com.coffeecode.domain.objects;

import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero(message = "Volume cannot be negative!")
    private final double cubicMeters;

    private Volume(double cubicMeters) {
        this.cubicMeters = cubicMeters;
    }

    public static Volume of(double cubicMeters) {
        Volume volume = new Volume(cubicMeters);
        ValidationUtils.validate(volume);
        return volume;
    }
}
