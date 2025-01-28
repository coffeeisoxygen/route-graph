package com.coffeecode.location.elevations.impl;

import com.coffeecode.location.elevations.api.ElevationApiClient;
import com.coffeecode.location.elevations.exception.ElevationException;
import com.coffeecode.location.elevations.http.ElevationsHttpClientException;
import com.coffeecode.location.elevations.http.HttpClientWrapper;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class DefaultApiClient implements ElevationApiClient {
    private static final String API_URL = "https://api.opentopodata.org/v1/aster30m";
    private final HttpClientWrapper httpClient;

    @Override
    public String getElevationData(double latitude, double longitude) throws ElevationException {
        try {
            String url = buildUrl(latitude, longitude);
            log.debug("Requesting elevation data from: {}", url);
            return httpClient.sendGetRequest(url);
        } catch (ElevationsHttpClientException e) {
            log.error("Failed to get elevation data for lat={}, lon={}", latitude, longitude, e);
            throw new ElevationException("Failed to get elevation data", e);
        }
    }

    private String buildUrl(double latitude, double longitude) {
        return String.format("%s?locations=%f,%f&interpolation=nearest",
                API_URL, latitude, longitude);
    }
}
