package com.coffeecode.domain.objects;

import javax.validation.constraints.NotNull;

import com.coffeecode.validation.ValidationUtils;

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

    @NotNull(message = "Length cannot be null")
    private final Distance length;

    @NotNull(message = "Capacity cannot be null")
    private final Volume capacity;

    private PipeProperties(Distance length, Volume capacity) {
        this.length = length;
        this.capacity = capacity;
    }

    public static PipeProperties of(Distance length, Volume capacity) {
        PipeProperties pipeProperties = new PipeProperties(length, capacity);
        ValidationUtils.validate(pipeProperties);
        return pipeProperties;
    }
}
