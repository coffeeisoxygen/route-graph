package com.coffeecode.location.elevations.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.coffeecode.exception.AppException;
import com.coffeecode.location.elevations.exception.ElevationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiClient {
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static String sendGetRequest(String url) throws AppException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ElevationException("API call failed with status: " + response.statusCode());
            }

            return response.body();
        } catch (IOException e) {
            throw new ElevationException("Failed to send request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ElevationException("Request was interrupted", e);
        }
    }

    private ApiClient() {
        throw new IllegalStateException("Utility class");
    }
}
