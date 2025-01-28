package com.coffeecode.location.elevations.impl;

import com.coffeecode.exception.AppException;
import com.coffeecode.location.elevations.api.ElevationApiClient;
import com.coffeecode.location.elevations.api.ElevationService;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.location.elevations.model.ElevationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultElevationService implements ElevationService {
    private final ElevationApiClient apiClient;
    private final ObjectMapper mapper;

    @Override
    public Elevation getElevation(double latitude, double longitude) {
        try {
            String response = apiClient.getElevationData(latitude, longitude);
            double meters = parseElevation(response);
            return Elevation.fromApi(meters);
        } catch (Exception e) {
            log.warn("Failed to get elevation, using default", e);
            return Elevation.defaultValue();
        }
    }

    @Override
    public Elevation updateElevation(double meters) {
        return Elevation.manual(meters);
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
