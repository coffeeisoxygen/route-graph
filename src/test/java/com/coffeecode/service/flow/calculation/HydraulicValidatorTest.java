package com.coffeecode.service.flow.calculation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Hydraulic Validator Tests")
class HydraulicValidatorTest {

    @Nested
    @DisplayName("Parameter Validation Tests")
    class ParameterValidationTests {
        @Test
        @DisplayName("Should validate valid hydraulic parameters")
        void shouldValidateHydraulicParameters() {
            assertDoesNotThrow(() -> HydraulicValidator.validateHydraulicParameters(100.0, 0.1, 1.0));
        }

        @Test
        @DisplayName("Should throw on invalid hydraulic parameters")
        void shouldThrowOnInvalidHydraulicParameters() {
            assertAll(
                    () -> assertThrows(ValidationException.class,
                            () -> HydraulicValidator.validateHydraulicParameters(-1.0, 0.1, 1.0),
                            "Should validate length"),
                    () -> assertThrows(ValidationException.class,
                            () -> HydraulicValidator.validateHydraulicParameters(100.0, -0.1, 1.0),
                            "Should validate diameter"),
                    () -> assertThrows(ValidationException.class,
                            () -> HydraulicValidator.validateHydraulicParameters(100.0, 0.1, -1.0),
                            "Should validate velocity"));
        }
    }

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {
        @ParameterizedTest
        @ValueSource(doubles = { 0.0, -1.0, -100.0 })
        @DisplayName("Should throw on non-positive values")
        void shouldThrowOnNonPositiveValues(double value) {
            assertAll(
                    () -> assertThrows(ValidationException.class,
                            () -> HydraulicValidator.validatePositiveValue("Test", value)),
                    () -> assertThrows(ValidationException.class, () -> HydraulicValidator.validatePressure(value)),
                    () -> assertThrows(ValidationException.class, () -> HydraulicValidator.validateHeadLoss(value)));
        }

        @Test
        @DisplayName("Should validate Reynolds number boundaries")
        void shouldValidateReynoldsNumberBoundaries() {
            assertAll(
                    () -> assertDoesNotThrow(() -> HydraulicValidator.validateReynoldsNumber(1000.0)),
                    () -> assertThrows(ValidationException.class, () -> HydraulicValidator.validateReynoldsNumber(1e9),
                            "Should throw on exceeding max Reynolds"));
        }
    }
}
