package com.coffeecode.domain.objects;

import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero(message = "Flow rate cannot be negative!")
    private final double flowRate; // Flow rate in cubic meters per second

    private WaterFlow(double flowRate) {
        this.flowRate = flowRate;
    }

    public static WaterFlow of(double flowRate) {
        WaterFlow waterFlow = new WaterFlow(flowRate);
        ValidationUtils.validate(waterFlow);
        return waterFlow;
    }
}
