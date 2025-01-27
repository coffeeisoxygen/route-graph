package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.domain.values.location.Distance;
import com.coffeecode.domain.values.pipe.PipeProperties;
import com.coffeecode.domain.values.water.WaterDemand;
import com.coffeecode.domain.values.water.WaterFlow;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Pipe Tests")
class PipeTest {

        private static class TestData {
                // Valid coordinates within range (-90 to 90 for latitude)
                static final WaterSource source = WaterSource.builder()
                                .name("Source")
                                .location(Coordinate.of(45.0, 45.0))
                                .capacity(WaterVolume.of(1000))
                                .flowRate(WaterFlow.of(0.5)) // Reduced flow rate
                                .build();

                static final WaterCustomer destination = WaterCustomer.builder()
                                .name("Customer")
                                .location(Coordinate.of(45.5, 45.5))
                                .waterDemand(WaterDemand.of(0.01)) // Fixed demand within limits
                                .build();

                static final PipeProperties properties = PipeProperties.standardPVC(
                                Distance.ofMeters(100));

                static Pipe.PipeBuilder validBuilder() {
                        return Pipe.builder()
                                        .source(source)
                                        .destination(destination)
                                        .properties(properties);
                }
        }

        @Nested
        @DisplayName("Creation Tests")
        class CreationTests {
                @Test
                @DisplayName("Should create with valid properties")
                void shouldCreateWithValidProperties() {
                        Pipe pipe = TestData.validBuilder().build();

                        assertAll(
                                        () -> assertNotNull(pipe.getId()),
                                        () -> assertEquals(TestData.source, pipe.getSource()),
                                        () -> assertEquals(TestData.destination, pipe.getDestination()),
                                        () -> assertEquals(TestData.properties, pipe.getProperties()));
                }
        }

        @Nested
        @DisplayName("Validation Tests")
        class ValidationTests {
                @Test
                @DisplayName("Should validate required properties")
                void shouldValidateRequiredProperties() {
                        assertAll(
                                        () -> assertThrows(ValidationException.class,
                                                        () -> Pipe.builder()
                                                                        .destination(TestData.destination)
                                                                        .properties(TestData.properties)
                                                                        .build(),
                                                        "Should validate source"),
                                        () -> assertThrows(ValidationException.class,
                                                        () -> Pipe.builder()
                                                                        .source(TestData.source)
                                                                        .properties(TestData.properties)
                                                                        .build(),
                                                        "Should validate destination"),
                                        () -> assertThrows(ValidationException.class,
                                                        () -> Pipe.builder()
                                                                        .source(TestData.source)
                                                                        .destination(TestData.destination)
                                                                        .build(),
                                                        "Should validate properties"));
                }

                @Test
                @DisplayName("Should prevent self-connection")
                void shouldPreventSelfConnection() {
                        assertThrows(ValidationException.class,
                                        () -> Pipe.builder()
                                                        .source(TestData.source)
                                                        .destination(TestData.source)
                                                        .properties(TestData.properties)
                                                        .build(),
                                        "Should prevent connecting node to itself");
                }
        }
}
