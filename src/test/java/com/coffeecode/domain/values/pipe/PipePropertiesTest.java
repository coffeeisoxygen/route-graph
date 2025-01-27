package com.coffeecode.domain.values.pipe;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.values.location.Distance;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("PipeProperties Tests")
class PipePropertiesTest {

        private static final Distance DEFAULT_LENGTH = Distance.ofMeters(100.0);
        private static final double VALID_DIAMETER = 0.1;

        @Nested
        @DisplayName("Creation Tests")
        class CreationTests {
                @Test
                @DisplayName("Should create with valid properties")
                void shouldCreateWithValidProperties() {
                        PipeProperties props = createValidProperties();
                        assertValidProperties(props);
                }

                private PipeProperties createValidProperties() {
                        return PipeProperties.builder()
                                        .material(PipeMaterial.PVC)
                                        .diameter(VALID_DIAMETER)
                                        .length(DEFAULT_LENGTH)
                                        .build();
                }

                private void assertValidProperties(PipeProperties props) {
                        assertAll(
                                        () -> assertEquals(PipeMaterial.PVC, props.getMaterial()),
                                        () -> assertEquals(VALID_DIAMETER, props.getDiameter()),
                                        () -> assertEquals(DEFAULT_LENGTH, props.getLength()),
                                        () -> assertEquals(PipeMaterial.PVC.getRoughness(), props.getRoughness()));
                }
        }

        @Nested
        @DisplayName("Validation Tests")
        class ValidationTests {
                @Test
                @DisplayName("Should validate all required properties")
                void shouldValidateAllProperties() {
                        assertAll(
                                        () -> assertThrows(ValidationException.class,
                                                        () -> PipeProperties.builder()
                                                                        .diameter(VALID_DIAMETER)
                                                                        .length(DEFAULT_LENGTH)
                                                                        .material(null)
                                                                        .build(),
                                                        "Should validate material"),
                                        () -> assertThrows(ValidationException.class,
                                                        () -> PipeProperties.builder()
                                                                        .material(PipeMaterial.PVC)
                                                                        .length(DEFAULT_LENGTH)
                                                                        .build(),
                                                        "Should validate diameter"),
                                        () -> assertThrows(ValidationException.class,
                                                        () -> PipeProperties.builder()
                                                                        .material(PipeMaterial.PVC)
                                                                        .diameter(VALID_DIAMETER)
                                                                        .build(),
                                                        "Should validate length"));
                }

                @Test
                @DisplayName("Should validate diameter range")
                void shouldValidateDiameterRange() {
                        assertAll(
                                        () -> assertThrows(ValidationException.class,
                                                        () -> PipeProperties.builder()
                                                                        .material(PipeMaterial.PVC)
                                                                        .diameter(0.01) // Too small
                                                                        .length(DEFAULT_LENGTH)
                                                                        .build()),
                                        () -> assertThrows(ValidationException.class,
                                                        () -> PipeProperties.builder()
                                                                        .material(PipeMaterial.PVC)
                                                                        .diameter(1.0) // Too large
                                                                        .length(DEFAULT_LENGTH)
                                                                        .build()));
                }
        }

        @Nested
        @DisplayName("Immutability Tests")
        class ImmutabilityTests {
                @Test
                @DisplayName("Should ensure properties immutability")
                void shouldEnsureImmutability() {
                        // Setup
                        Distance originalLength = Distance.ofMeters(100.0);
                        PipeProperties props = PipeProperties.builder()
                                        .material(PipeMaterial.PVC)
                                        .diameter(VALID_DIAMETER)
                                        .length(originalLength)
                                        .build();

                        // Verify immutability
                        assertAll(
                                        "Properties should be immutable",
                                        () -> assertNotSame(originalLength, props.getLength(),
                                                        "Length should be defensively copied"),
                                        () -> assertEquals(originalLength.getMetersValue(),
                                                        props.getLength().getMetersValue(),
                                                        "Length value should remain unchanged"),
                                        () -> assertEquals(PipeMaterial.PVC, props.getMaterial(),
                                                        "Material should remain unchanged"),
                                        () -> assertEquals(VALID_DIAMETER, props.getDiameter(),
                                                        "Diameter should remain unchanged"),
                                        () -> assertEquals(PipeMaterial.PVC.getRoughness(), props.getRoughness(),
                                                        "Roughness should match material"));
                }
        }
}
