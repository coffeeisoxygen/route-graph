package com.coffeecode.service.flow;

import org.springframework.stereotype.Service;

import com.coffeecode.domain.entities.Pipe;
import com.coffeecode.validation.exceptions.ValidationException;

/**
 * Service interface for flow calculations in the network.
 */
@Service
public interface FlowCalculationService {

    /**
     * Calculates flow parameters for a given pipe and input pressure
     * 
     * @param pipe       The pipe to calculate flow for
     * @param pressureIn Input pressure in Pascals
     * @return FlowResult containing calculated parameters
     * @throws ValidationException if parameters are invalid
     */
    FlowResult calculateFlow(Pipe pipe, double pressureIn);
}
