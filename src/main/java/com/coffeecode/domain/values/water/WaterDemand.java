package com.coffeecode.domain.values.water;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

/**
 * Represents daily water demand volume.
 */

@Value
public class WaterDemand {

    private final WaterVolume dailyDemand;

    private WaterDemand(WaterVolume dailyDemand) {
        validateDailyDemand(dailyDemand);
        this.dailyDemand = dailyDemand;
    }

    public static WaterDemand of(WaterVolume dailyDemand) {
        return new WaterDemand(dailyDemand);
    }

    private static void validateDailyDemand(WaterVolume dailyDemand) {
        if (dailyDemand == null) {
            throw new ValidationException("Daily demand cannot be null");
        }
    }
}
