package com.coffeecode.location.elevations.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
