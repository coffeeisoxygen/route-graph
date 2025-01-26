package com.coffeecode.domain.objects;

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

    public WaterFlow(double flowRate) {
        if (flowRate < 0) {
            throw new IllegalArgumentException("Flow rate cannot be negative!");
        }
        this.flowRate = flowRate;
    }
}
