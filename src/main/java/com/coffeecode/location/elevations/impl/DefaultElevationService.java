package com.coffeecode.location.elevations.impl;

import com.coffeecode.exception.AppException;
import com.coffeecode.location.elevations.api.ElevationService;
import com.coffeecode.location.elevations.dto.ElevationResponse;
import com.coffeecode.location.elevations.exception.ElevationException;
import com.coffeecode.location.elevations.model.Elevation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultElevationService implements ElevationService {
    private static final String API_URL = "https://api.opentopodata.org/v1/aster30m";
    private final ApiClient apiClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public DefaultElevationService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Elevation getElevation(double latitude, double longitude) {
        try {
            String url = buildApiUrl(latitude, longitude);
            String response = ApiClient.sendGetRequest(url);
            double meters = parseElevation(response);

            log.info("Got elevation from API: {}m for lat={}, lon={}",
                    meters, latitude, longitude);
            return Elevation.fromApi(meters);

        } catch (ElevationException e) {
            log.warn("Failed to get elevation from API, using default", e);
            return Elevation.defaultValue();
        }
    }

    @Override
    public Elevation updateElevation(double meters) {
        return Elevation.manual(meters);
    }

    private String buildApiUrl(double latitude, double longitude) {
        return String.format("%s?locations=%f,%f&interpolation=cubic",
                API_URL, latitude, longitude);
    }

    private double parseElevation(String response) throws AppException {
        try {
            ElevationResponse elevationResponse = mapper.readValue(response, ElevationResponse.class);
            if (!"OK".equals(elevationResponse.getStatus())) {
                throw new AppException("API returned non-OK status");
            }
            if (elevationResponse.getResults() == null || elevationResponse.getResults().isEmpty()) {
                throw new AppException("No elevation data in response");
            }
            return elevationResponse.getResults().get(0).getElevation();
        } catch (JsonProcessingException e) {
            throw new AppException("Failed to parse elevation response", e);
        }
    }
}
