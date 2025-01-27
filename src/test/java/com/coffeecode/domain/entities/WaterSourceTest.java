package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.domain.values.location.Elevation;
import com.coffeecode.domain.values.water.WaterFlow;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Water Source Tests")
class WaterSourceTest {

    @Nested
    @DisplayName("Basic Properties")
    class BasicPropertiesTests {
        private WaterSource createValidSource() {
            return WaterSource.builder()
                    .name("Test Source")
                    .location(Coordinate.of(0, 0))
                    .elevation(Elevation.of(100.0))
                    .capacity(WaterVolume.of(1000.0))
                    .flowRate(WaterFlow.of(10.0))
                    .build();
        }

        @Test
        @DisplayName("Should create with valid properties")
        void shouldCreateWithValidProperties() {
            WaterSource source = createValidSource();

            assertNotNull(source);
            assertEquals("Test Source", source.getName());
            assertEquals(1000.0, source.getCapacity().getValue());
            assertEquals(10.0, source.getFlowRate().getValue());
            assertEquals(NodeType.SOURCE, source.getType());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {
        @Test
        @DisplayName("Should validate capacity before flow")
        void shouldValidateCapacityBeforeFlow() {
            assertThrows(ValidationException.class, () -> WaterSource.builder()
                    .name("Test Source")
                    .location(Coordinate.of(0, 0))
                    .flowRate(WaterFlow.of(10.0))
                    .build());
        }

        @Test
        @DisplayName("Should validate flow against capacity")
        void shouldValidateFlowAgainstCapacity() {
            assertThrows(ValidationException.class, () -> WaterSource.builder()
                    .name("Test Source")
                    .location(Coordinate.of(0, 0))
                    .capacity(WaterVolume.of(10.0))
                    .flowRate(WaterFlow.of(20.0))
                    .build());
        }
    }
}
