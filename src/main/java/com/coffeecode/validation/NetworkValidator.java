package com.coffeecode.validation;

import java.util.List;

import com.coffeecode.core.NetworkTopology;

public interface NetworkValidator {
    boolean isValid(NetworkTopology topology);

    List<ValidationError> validate(NetworkTopology topology);
}
