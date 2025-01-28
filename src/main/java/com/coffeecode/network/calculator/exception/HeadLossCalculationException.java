package com.coffeecode.network.calculator.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeadLossCalculationException extends RuntimeException {
    public HeadLossCalculationException(String message) {
        super(message);
        log.error(message);
    }

    public HeadLossCalculationException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }

}
