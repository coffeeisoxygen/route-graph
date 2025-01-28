package com.coffeecode.location.elevations.http;

public class ElevationsHttpClientException extends Exception {
    public ElevationsHttpClientException(String message) {
        super(message);
    }

    public ElevationsHttpClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
