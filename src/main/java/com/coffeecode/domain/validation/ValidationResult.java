package com.coffeecode.domain.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationResult {
    private final boolean valid;
    private final List<String> errors;

    public static ValidationResult valid() {
        return ValidationResult.builder()
                .valid(true)
                .errors(Collections.emptyList())
                .build();
    }

    public static ValidationResult invalid(String error) {
        return invalid(Collections.singletonList(error));
    }

    public static ValidationResult invalid(List<String> errors) {
        return ValidationResult.builder()
                .valid(false)
                .errors(new ArrayList<>(errors))
                .build();
    }

    public boolean hasErrors() {
        return !valid;
    }
}
