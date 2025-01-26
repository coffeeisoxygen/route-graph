package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Distance;
import com.coffeecode.domain.objects.PipeProperties;
import com.coffeecode.domain.objects.Volume;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Pipe Tests")
class PipeTest {

    private NetworkNode source;
    private NetworkNode destination;
    private PipeProperties properties;

    @BeforeEach
    void setUp() {
        Coordinate location = Coordinate.of(0.0, 0.0);
        source = WaterSource.builder()
                .name("Source1")
                .location(location)
                .capacity(Volume.of(1000.0))
                .build();

        destination = Customer.builder()
                .name("Customer1")
                .location(Coordinate.of(1.0, 1.0))
                .waterDemand(WaterDemand.of(Volume.of(100.0)))
                .build();

        properties = PipeProperties.of(
                Distance.of(100.0),
                Volume.of(500.0)
        );
    }

    @Test
    @DisplayName("Should create pipe with valid parameters")
    void shouldCreatePipeWithValidParameters() {
        Pipe pipe = Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();

        assertNotNull(pipe.getId());
        assertEquals(source, pipe.getSource());
        assertEquals(destination, pipe.getDestination());
        assertEquals(properties, pipe.getProperties());
    }

    @Test
    @DisplayName("Should throw ValidationException for null source")
    void shouldThrowValidationExceptionForNullSource() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Pipe.builder()
                        .destination(destination)
                        .properties(properties)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Source node cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null destination")
    void shouldThrowValidationExceptionForNullDestination() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Pipe.builder()
                        .source(source)
                        .properties(properties)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Destination node cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for same source and destination")
    void shouldThrowValidationExceptionForSameSourceAndDestination() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Pipe.builder()
                        .source(source)
                        .destination(source)
                        .properties(properties)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Source and destination cannot be the same node"));
    }

    @Test
    @DisplayName("Should generate unique IDs")
    void shouldGenerateUniqueIds() {
        Pipe pipe1 = Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();

        Pipe pipe2 = Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();

        assertNotEquals(pipe1.getId(), pipe2.getId());
    }
}
