package com.coffeecode.logic.flow;

import com.coffeecode.domain.entity.Pipe;

/**
 * Service interface for flow calculations in the network.
 */
public interface FlowCalculationService {

    /**
     * Calculates flow parameters for a given pipe and input pressure.
     *
     * @param pipe The pipe to calculate flow for
     * @param pressureIn Input pressure in Pascals
     * @return FlowResult containing calculated parameters
     */
    FlowResult calculateFlow(Pipe pipe, double pressureIn);
}
