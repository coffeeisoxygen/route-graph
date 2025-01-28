package com.coffeecode.infrastructure.validation.exception;

import lombok.Getter;

@Getter
public class NetworkValidationException extends RuntimeException {
    private final String code;

    public NetworkValidationException(String code, String message) {
        super(message);
        this.code = code;
    }
}
