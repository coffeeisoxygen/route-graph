package com.coffeecode.location.elevations.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.coffeecode.location.elevations.api.ElevationApiClient;
import com.coffeecode.location.elevations.exception.ElevationException;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class DefaultApiClient implements ElevationApiClient {
    private static final String API_URL = "https://api.opentopodata.org/v1/aster30m";
    private final HttpClient httpClient;

    public DefaultApiClient(HttpClient httpClient) {
        this.httpClient = httpClient != null ? httpClient
                : HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
    }

    @Override
    public String getElevationData(double latitude, double longitude) throws ElevationException {
        try {
            String url = buildUrl(latitude, longitude);
            return sendRequest(url);
        } catch (Exception e) {
            log.error("Failed to get elevation data", e);
            throw new ElevationException("Failed to get elevation data", e);
        }
    }

    private String buildUrl(double latitude, double longitude) {
        return String.format("%s?locations=%f,%f&interpolation=nearest",
                API_URL, latitude, longitude);
    }

    private String sendRequest(String url) throws ElevationException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
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
}
