package com.coffeecode.location.elevations.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("ElevationResponse Tests")
class ElevationResponseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should deserialize valid JSON response")
    void shouldDeserializeValidResponse() throws Exception {
        // Given
        String json = """
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

        // When
        ElevationResponse response = mapper.readValue(json, ElevationResponse.class);

        // Then
        assertEquals("OK", response.getStatus());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
        assertEquals(325.0, response.getResults().get(0).getElevation());
    }

    @Test
    @DisplayName("Should handle empty results array")
    void shouldHandleEmptyResults() throws Exception {
        // Given
        String json = """
                {
                    "results": [],
                    "status": "OK"
                }""";

        // When
        ElevationResponse response = mapper.readValue(json, ElevationResponse.class);

        // Then
        assertTrue(response.getResults().isEmpty());
        assertEquals("OK", response.getStatus());
    }

    @Test
    @DisplayName("Should handle missing fields")
    void shouldHandleMissingFields() throws Exception {
        // Given
        String json = """
                {
                    "results": [{
                        "elevation": 325.0
                    }],
                    "status": "OK"
                }""";

        // When
        ElevationResponse response = mapper.readValue(json, ElevationResponse.class);

        // Then
        assertNotNull(response.getResults());
        assertNull(response.getResults().get(0).getDataset());
        assertEquals(325.0, response.getResults().get(0).getElevation());
    }

    @Test
    @DisplayName("Should throw exception for invalid JSON")
    void shouldThrowExceptionForInvalidJson() {
        // Given
        String invalidJson = "{ invalid json }";

        // Then
        JsonProcessingException exception = assertThrows(JsonProcessingException.class,
                () -> mapper.readValue(invalidJson, ElevationResponse.class));
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle non-OK status")
    void shouldHandleNonOkStatus() throws Exception {
        // Given
        String json = """
                {
                    "results": [],
                    "status": "ERROR"
                }""";

        // When
        ElevationResponse response = mapper.readValue(json, ElevationResponse.class);

        // Then
        assertEquals("ERROR", response.getStatus());
    }
}
