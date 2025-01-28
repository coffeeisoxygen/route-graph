package com.coffeecode.location.elevations.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.location.elevations.exception.ElevationException;
import com.coffeecode.location.elevations.http.ElevationsHttpClientException;
import com.coffeecode.location.elevations.http.HttpClientWrapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultApiClient Tests")
class DefaultApiClientTest {

    @Mock
    private HttpClientWrapper httpClient;
    private DefaultApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient = DefaultApiClient.builder()
                .httpClient(httpClient)
                .build();
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
        when(httpClient.sendGetRequest(anyString())).thenReturn(expectedResponse);

        // When
        String response = apiClient.getElevationData(-6.7991455, 107.1884536);

        // Then
        assertEquals(expectedResponse, response);
        verify(httpClient).sendGetRequest(contains("aster30m"));
    }

    @Test
    @DisplayName("Should throw exception when HTTP client fails")
    void shouldThrowExceptionOnHttpClientFailure() throws Exception {
        // Given
        when(httpClient.sendGetRequest(anyString()))
                .thenThrow(new ElevationsHttpClientException("Network error"));

        // When/Then
        assertThrows(ElevationException.class, () -> apiClient.getElevationData(-6.7991455, 107.1884536));
    }
}
