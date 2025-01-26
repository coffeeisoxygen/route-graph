package com.coffeecode.domain.objects;

import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a distance measurement in meters. This class is immutable and
 * ensures that the distance value is non-negative.
 *
 * <p>
 * Example usage:</p>
 * <pre>
 * {@code
 * Distance distance = new Distance(100.0);
 * System.out.println(distance.getValue()); // 100.0
 * }
 * </pre>
 *
 * <p>
 * Annotations used:</p>
 * <ul>
 * <li>{@code @Getter} - Generates getter methods for all fields.</li>
 * <li>{@code @ToString} - Generates a {@code toString()} method.</li>
 * <li>{@code @EqualsAndHashCode} - Generates {@code equals()} and
 * {@code hashCode()} methods.</li>
 * </ul>
 *
 * @throws IllegalArgumentException if the distance value is negative.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Distance {

    @PositiveOrZero(message = "Distance cannot be negative!")
    private final double value; // Distance in meters

    private Distance(double value) {
        this.value = value;
    }

    public static Distance of(double value) {
        Distance distance = new Distance(value);
        ValidationUtils.validate(distance);
        return distance;
    }
}
