package com.coffeecode.domain.objects;

import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Daily demand cannot be null")
    private final Volume dailyDemand;

    private WaterDemand(Volume dailyDemand) {
        this.dailyDemand = dailyDemand;
    }

    public static WaterDemand of(Volume dailyDemand) {
        WaterDemand waterDemand = new WaterDemand(dailyDemand);
        ValidationUtils.validate(waterDemand);
        return waterDemand;
    }
}
