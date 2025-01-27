package com.coffeecode.domain.values;

import com.coffeecode.domain.constants.ElevationRangeConstants;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Elevation {

    private final double value;

    public Elevation(Double value) {
        if (value == null) {
            this.value = ElevationRangeConstants.MIN.getValue();
        } else {
            this.value = value;
        }
    }

    public static Elevation of(Double value) {
        return new Elevation(value);
    }
}
