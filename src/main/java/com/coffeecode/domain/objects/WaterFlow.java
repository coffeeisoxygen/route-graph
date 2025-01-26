package com.coffeecode.domain.objects;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the flow of water with a specified flow rate. The flow rate is
 * measured in cubic meters per second.
 *
 * <p>
 * This class is immutable and thread-safe.</p>
 *
 * <p>
 * Example usage:</p>
 * <pre>
 *     WaterFlow waterFlow = new WaterFlow(5.0);
 *     System.out.println(waterFlow.getFlowRate());
 * </pre>
 *
 * @author
 * @version 1.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class WaterFlow {

    private final double flowRate; // Flow rate in cubic meters per second

    private WaterFlow(double flowRate) {
        validateFlowRate(flowRate);
        this.flowRate = flowRate;
    }

    public static WaterFlow of(double flowRate) {
        return new WaterFlow(flowRate);
    }

    private static void validateFlowRate(double flowRate) {
        if (flowRate < 0) {
            throw new ValidationException("Flow rate cannot be negative!");
        }
    }
}
