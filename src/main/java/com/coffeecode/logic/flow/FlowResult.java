package com.coffeecode.logic.flow;

import lombok.Builder;
import lombok.Value;

/**
 * Immutable class representing flow calculation results.
 */
@Value
@Builder
public class FlowResult {

    double flowRate;        // m³/s
    double pressureOut;     // Pascal
    double velocityOut;     // m/s
    double headLoss;        // meters
}
