package com.coffeecode.infrastructure.validation;

import java.util.List;

import com.coffeecode.domain.topology.NetworkTopology;

public interface NetworkValidator {
    boolean isValid(NetworkTopology topology);

    List<ValidationError> validate(NetworkTopology topology);
}
