package com.coffeecode.domain.objects;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents daily water demand volume.
 */
@Getter
@ToString
@EqualsAndHashCode
public class WaterDemand {

    private final Volume dailyDemand;

    private WaterDemand(Volume dailyDemand) {
        validateDailyDemand(dailyDemand);
        this.dailyDemand = dailyDemand;
    }

    public static WaterDemand of(Volume dailyDemand) {
        return new WaterDemand(dailyDemand);
    }

    private static void validateDailyDemand(Volume dailyDemand) {
        if (dailyDemand == null) {
            throw new ValidationException("Daily demand cannot be null");
        }
    }
}
