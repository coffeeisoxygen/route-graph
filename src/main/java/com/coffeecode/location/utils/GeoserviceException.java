package com.coffeecode.location.utils;

public class GeoserviceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GeoserviceException(String message) {
        super(message);

    }

    public GeoserviceException(String message, Throwable cause) {
        super(message, cause);

    }

}
