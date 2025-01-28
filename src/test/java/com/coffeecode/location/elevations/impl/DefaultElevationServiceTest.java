package com.coffeecode.location.elevations.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.location.elevations.api.ElevationApiClient;
import com.coffeecode.location.elevations.exception.ElevationException;
import com.coffeecode.location.elevations.model.Elevation;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultElevationService Tests")
class DefaultElevationServiceTest {

    @Mock
    private ElevationApiClient apiClient;
    private ObjectMapper mapper;
    private DefaultElevationService service;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        service = new DefaultElevationService(apiClient, mapper);
    }

    @Test
    @DisplayName("Should get elevation from API")
    void shouldGetElevationFromApi() throws Exception {
        // Given
        String response = """
                {
                    "results": [{
                        "dataset": "aster30m",
                        "elevation": 325.0,
                        "location": {
                            "lat": -6.7991455,
                            "lng": 107.1884536
                        }
                    }],
                    "status": "OK"
                }""";
        when(apiClient.getElevationData(anyDouble(), anyDouble()))
                .thenReturn(response);

        // When
        var elevation = service.getElevation(-6.7991455, 107.1884536);

        // Then
        assertEquals(325.0, elevation.getMeters());
        assertEquals(Elevation.Type.API, elevation.getType());
    }

    @Test
    @DisplayName("Should return default on API failure")
    void shouldReturnDefaultOnFailure() throws Exception {
        // Given
        when(apiClient.getElevationData(anyDouble(), anyDouble()))
                .thenThrow(new ElevationException("API Error"));

        // When
        var elevation = service.getElevation(-6.7991455, 107.1884536);

        // Then
        assertEquals(0.0, elevation.getMeters());
        assertEquals(Elevation.Type.DEFAULT, elevation.getType());
    }

    @Test
    @DisplayName("Should handle JSON parsing error")
    void shouldHandleParsingError() throws Exception {
        when(apiClient.getElevationData(anyDouble(), anyDouble()))
            .thenReturn("invalid json");

        var elevation = service.getElevation(-6.7991455, 107.1884536);
        assertEquals(Elevation.Type.DEFAULT, elevation.getType());
    }
}
