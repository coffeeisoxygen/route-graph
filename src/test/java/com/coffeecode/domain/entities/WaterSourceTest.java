package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.domain.values.location.Elevation;
import com.coffeecode.domain.values.water.WaterFlow;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Getter;
import lombok.Value;

@DisplayName("WaterSource Tests")
class WaterSourceTest {

    @Value
    @Getter
    private static class WaterSourceAsserter {

        WaterSource source;

        static WaterSourceAsserter assertThat(WaterSource source) {
            return new WaterSourceAsserter(source);
        }

        WaterSourceAsserter hasCapacity(double expected) {
            assertEquals(expected, source.getCapacity().getValue(),
                    "Water source capacity should match");
            return this;
        }

        WaterSourceAsserter hasFlowRate(double expected) {
            assertEquals(expected, source.getFlowRate().getValue(),
                    "Water source flow rate should match");
            return this;
        }

        WaterSourceAsserter hasName(String expected) {
            assertEquals(expected, source.getName(),
                    "Water source name should match");
            return this;
        }
    }

    @Nested
    @DisplayName("Creation Tests")
    class CreationTests {

        @Test
        @DisplayName("Should create water source with valid parameters")
        void shouldCreateWaterSourceWithValidParameters() {
            WaterSource source = WaterSource.builder()
                    .name("Test Source")
                    .capacity(WaterVolume.of(1000.0))
                    .flowRate(WaterFlow.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .build();

            WaterSourceAsserter.assertThat(source)
                    .hasName("Test Source")
                    .hasCapacity(1000.0)
                    .hasFlowRate(0.5);
        }

        @Test
        @DisplayName("Should create water source with default elevation")
        void shouldCreateWaterSourceWithDefaultElevation() {
            WaterSource source = WaterSource.builder()
                    .name("Test Source")
                    .capacity(WaterVolume.of(1000.0))
                    .flowRate(WaterFlow.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .build();

            assertEquals(OperationalLimits.ElevationLimits.DEFAULT,
                    source.getElevation().getValue());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw on null name")
        void shouldThrowOnNullName() {
            assertThrows(ValidationException.class, () -> WaterSource.builder()
                    .capacity(WaterVolume.of(1000.0))
                    .flowRate(WaterFlow.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .build());
        }

        @Test
        @DisplayName("Should throw on invalid name length")
        void shouldThrowOnInvalidNameLength() {
            assertThrows(ValidationException.class, () -> WaterSource.builder()
                    .name("ab") // Too short
                    .capacity(WaterVolume.of(1000.0))
                    .flowRate(WaterFlow.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .build());
        }

        @Test
        @DisplayName("Should throw on null capacity")
        void shouldThrowOnNullCapacity() {
            assertThrows(ValidationException.class, () -> WaterSource.builder()
                    .name("Test Source")
                    .flowRate(WaterFlow.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .build());
        }

        @Test
        @DisplayName("Should throw on null flow rate")
        void shouldThrowOnNullFlowRate() {
            assertThrows(ValidationException.class, () -> WaterSource.builder()
                    .name("Test Source")
                    .capacity(WaterVolume.of(1000.0))
                    .location(Coordinate.of(0, 0))
                    .build());
        }
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {

        @Test
        @DisplayName("Should allow chaining builder methods")
        void shouldAllowChainingBuilderMethods() {
            WaterSource source = WaterSource.builder()
                    .name("Test Source")
                    .capacity(WaterVolume.of(1000.0))
                    .flowRate(WaterFlow.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .elevation(Elevation.of(100.0))
                    .build();

            assertNotNull(source);
            assertEquals(100.0, source.getElevation().getValue());
        }
    }
}
