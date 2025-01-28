package com.coffeecode.location.elevations.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.location.elevations.exception.ElevationException;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultApiClient Tests")
class DefaultApiClientTest {

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse<Object> httpResponse;

    private DefaultApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient = new DefaultApiClient(httpClient);
    }

    @Test
    @DisplayName("Should get elevation data successfully")
    void shouldGetElevationData() throws Exception {
        // Given
        String expectedResponse = """
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

        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(expectedResponse);
        when(httpClient.send(any(), any())).thenReturn(httpResponse);

        // When
        String response = apiClient.getElevationData(-6.7991455, 107.1884536);

        // Then
        assertEquals(expectedResponse, response);
        verify(httpClient).send(any(), any());
    }

    @Test
    @DisplayName("Should throw exception when API returns error")
    void shouldThrowExceptionOnApiError() throws Exception {
        // Given
        when(httpResponse.statusCode()).thenReturn(500);
        when(httpClient.send(any(), any())).thenReturn(httpResponse);

        // When/Then
        ElevationException exception = assertThrows(ElevationException.class,
                () -> apiClient.getElevationData(-6.7991455, 107.1884536));
        assertEquals("Expected error message", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception on network error")
    void shouldThrowExceptionOnNetworkError() throws Exception {
        // Given
        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Network error"));

        // When/Then
        ElevationException exception = assertThrows(ElevationException.class,
                () -> apiClient.getElevationData(-6.7991455, 107.1884536));
        assertEquals("Network error", exception.getMessage());
    }
}
