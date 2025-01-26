package com.coffeecode.domain.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a volume measurement in cubic meters. This class is immutable and
 * ensures that the volume cannot be negative.
 *
 * <p>
 * Example usage:</p>
 * <pre>{@code
 * Volume volume = new Volume(5.0);
 * }</pre>
 *
 * <p>
 * Annotations used:</p>
 * <ul>
 * <li>{@code @Getter} - Generates getter methods for all fields.</li>
 * <li>{@code @ToString} - Generates a toString method.</li>
 * <li>{@code @EqualsAndHashCode} - Generates equals and hashCode methods.</li>
 * </ul>
 *
 * @throws IllegalArgumentException if the provided volume is negative
 */
@Getter
@ToString(of = "cubicMeters")
@EqualsAndHashCode
public class Volume {

    private final double cubicMeters;

    public Volume(double cubicMeters) {
        if (cubicMeters < 0) {
            throw new IllegalArgumentException("Volume cannot be negative!");
        }
        this.cubicMeters = cubicMeters;
    }
}
