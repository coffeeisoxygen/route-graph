package com.coffeecode.domain.values.water;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Represents a volume measurement in cubic meters.
 */
@Value
@AllArgsConstructor(staticName = "of")
public class WaterVolume {
    double value;
}
