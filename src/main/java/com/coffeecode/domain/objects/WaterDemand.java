package com.coffeecode.domain.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the water demand for a specific entity. This class encapsulates
 * the daily water demand volume.
 *
 * <p>
 * Instances of this class are immutable.</p>
 *
 * <p>
 * Example usage:</p>
 * <pre>
 * {@code
 * Volume dailyVolume = new Volume(100);
 * WaterDemand waterDemand = new WaterDemand(dailyVolume);
 * }
 * </pre>
 *
 * <p>
 * Note: The daily demand volume cannot be null.</p>
 *
 * @see Volume
 */
@Getter
@ToString
@EqualsAndHashCode
public class WaterDemand {

    private final Volume dailyDemand;

    public WaterDemand(Volume dailyDemand) {
        if (dailyDemand == null) {
            throw new IllegalArgumentException("Daily demand cannot be null!");
        }
        this.dailyDemand = dailyDemand;
    }
}
