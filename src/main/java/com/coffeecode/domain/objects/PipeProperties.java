package com.coffeecode.domain.objects;

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

    public PipeProperties(Distance length, Volume capacity) {
        if (length == null || capacity == null) {
            throw new IllegalArgumentException("Length and capacity cannot be null!");
        }
        this.length = length;
        this.capacity = capacity;
    }
}
