package com.coffeecode.infrastructure.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationError {
    private final String code;
    private final String message;
    private final Severity severity;

    public enum Severity {
        WARNING, ERROR
    }
}
