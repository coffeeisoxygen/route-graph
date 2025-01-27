package com.coffeecode.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
        log.error(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }

}
