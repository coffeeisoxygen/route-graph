package com.coffeecode.domain.objects;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the properties of a pipe, including its length and capacity. This
 * class is immutable and ensures that both length and capacity are non-null.
 *
 * <p>
 * Usage example:</p>
 * <pre>
 * {@code
 * Distance length = new Distance(100);
 * Volume capacity = new Volume(50);
 * PipeProperties pipeProperties = new PipeProperties(length, capacity);
 * }
 * </pre>
 *
 * <p>
 * Note: Both {@code length} and {@code capacity} must be non-null.</p>
 *
 *
 */
@Getter
@ToString
@EqualsAndHashCode
public class PipeProperties {

    private final Distance length;
    private final Volume capacity;

    private PipeProperties(Distance length, Volume capacity) {
        validateLength(length);
        validateCapacity(capacity);
        this.length = length;
        this.capacity = capacity;
    }

    public static PipeProperties of(Distance length, Volume capacity) {
        return new PipeProperties(length, capacity);
    }

    private static void validateLength(Distance length) {
        if (length == null) {
            throw new ValidationException("Length cannot be null");
        }
    }

    private static void validateCapacity(Volume capacity) {
        if (capacity == null) {
            throw new ValidationException("Capacity cannot be null");
        }
    }
}
