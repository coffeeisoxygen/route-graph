package com.coffeecode.location.elevations.exception;

public class ElevationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ElevationException(String message) {
        super(message);

    }

    public ElevationException(String message, Throwable cause) {
        super(message, cause);

    }

}
