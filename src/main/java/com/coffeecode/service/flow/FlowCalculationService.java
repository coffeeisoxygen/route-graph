package com.coffeecode.service.flow;

import com.coffeecode.domain.entities.Pipe;

/**
 * Service interface for flow calculations in the network.
 */
public interface FlowCalculationService {

    /**
     * Calculates flow parameters for a given pipe and input pressure.
     *
     * @param pipe       The pipe to calculate flow for
     * @param pressureIn Input pressure in Pascals
     * @return FlowResult containing calculated parameters
     */
    FlowResult calculateFlow(Pipe pipe, double pressureIn);
}
