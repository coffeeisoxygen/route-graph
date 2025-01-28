package com.coffeecode.location.elevations.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.location.elevations.exception.ElevationException;
import com.coffeecode.location.elevations.model.Elevation;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Elevation Service Tests")
class DefaultElevationServiceTest {

    @Mock
    private ApiClient apiClient;

    private DefaultElevationService service;

    @BeforeEach
    void setUp() {
        service = new DefaultElevationService(apiClient);
    }

    @Test
    @DisplayName("Should get elevation from API")
    void shouldGetElevationFromApi() throws Exception {
        // Given
        String response = """
                {"results":[{"elevation":100.0}]}
                """;
        when(ApiClient.sendGetRequest(any())).thenReturn(response);

        // When
        var elevation = service.getElevation(-6.2088, 106.8456);

        // Then
        assertEquals(100.0, elevation.getMeters());
        assertEquals(Elevation.Type.API, elevation.getType());
    }

    @Test
    @DisplayName("Should fallback to default on API failure")
    void shouldFallbackToDefault() throws Exception {
        // Given
        when(ApiClient.sendGetRequest(any())).thenThrow(new ElevationException("API Error"));

        // When
        var elevation = service.getElevation(-6.2088, 106.8456);

        // Then
        assertEquals(0.0, elevation.getMeters());
        assertEquals(Elevation.Type.DEFAULT, elevation.getType());
    }
}
