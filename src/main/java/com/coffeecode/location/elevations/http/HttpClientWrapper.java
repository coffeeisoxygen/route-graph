package com.coffeecode.location.elevations.http;

public interface HttpClientWrapper {
    String sendGetRequest(String url) throws ElevationsHttpClientException;
}
