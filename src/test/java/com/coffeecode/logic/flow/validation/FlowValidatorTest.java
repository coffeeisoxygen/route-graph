package com.coffeecode.logic.flow.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.entity.Customer;
import com.coffeecode.domain.entity.NetworkNode;
import com.coffeecode.domain.entity.Pipe;
import com.coffeecode.domain.entity.WaterSource;
import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Distance;
import com.coffeecode.domain.objects.PipeProperties;
import com.coffeecode.domain.objects.Volume;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.validation.exceptions.ValidationException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Flow Validator Tests")
class FlowValidatorTest {

    private Pipe createValidPipe() {
        PipeProperties properties = createValidProperties();
        NetworkNode source = createValidSource();
        NetworkNode destination = createValidDestination();
        return Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();
    }

    private WaterSource createValidSource() {
        return WaterSource.builder()
                .name("Test Source")
                .location(Coordinate.of(0.0, 0.0))
                .capacity(Volume.of(1000.0))
                .build();
    }

    private Customer createValidDestination() {
        return Customer.builder()
                .name("Test Customer")
                .location(Coordinate.of(1.0, 1.0))
                .waterDemand(WaterDemand.of(Volume.of(100.0)))
                .build();
    }

    private PipeProperties createValidProperties() {
        return PipeProperties.builder()
                .length(Distance.of(100.0))
                .capacity(Volume.of(500.0))
                .diameter(0.5)
                .roughness(0.0002)
                .build();
    }

    @Test
    @DisplayName("Should throw exception when pipe is null")
    void validatePipe_shouldThrowException_whenPipeIsNull() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> FlowValidator.validatePipe(null)
        );
        assertEquals("Pipe cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should not throw exception when pipe is valid")
    void validatePipe_shouldNotThrowException_whenPipeIsValid() {
        Pipe validPipe = createValidPipe();
        assertDoesNotThrow(() -> FlowValidator.validatePipe(validPipe));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -0.1, -100.0})
    @DisplayName("Should throw exception for negative pressures")
    void validatePressure_shouldThrowException_whenPressureIsNegative(double pressure) {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> FlowValidator.validatePressure(pressure)
        );
        assertEquals("Pressure cannot be negative", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.1, 1.0, 100.0})
    @DisplayName("Should accept non-negative pressures")
    void validatePressure_shouldNotThrowException_whenPressureIsNonNegative(double pressure) {
        assertDoesNotThrow(() -> FlowValidator.validatePressure(pressure));
    }

    @Test
    @DisplayName("Should not instantiate utility class")
    void shouldNotInstantiateUtilityClass() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            throw new IllegalStateException("Utility class");
        });
        assertEquals("Utility class", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when pipe properties is null")
    void validatePipe_shouldThrowException_whenPipePropertiesIsNull() {
        NetworkNode source = createValidSource();
        NetworkNode destination = createValidDestination();

        Pipe invalidPipe = createTestPipe(source, destination, null);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> FlowValidator.validatePipe(invalidPipe)
        );
        assertEquals("Pipe properties cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when pipe source is null")
    void validatePipe_shouldThrowException_whenPipeSourceIsNull() {
        NetworkNode destination = createValidDestination();
        PipeProperties properties = createValidProperties();

        Pipe invalidPipe = createTestPipe(null, destination, properties);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> FlowValidator.validatePipe(invalidPipe)
        );
        assertEquals("Pipe must have source and destination", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when pipe destination is null")
    void validatePipe_shouldThrowException_whenPipeDestinationIsNull() {
        NetworkNode source = createValidSource();
        PipeProperties properties = createValidProperties();

        Pipe invalidPipe = createTestPipe(source, null, properties);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> FlowValidator.validatePipe(invalidPipe)
        );
        assertEquals("Pipe must have source and destination", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for zero pipe length")
    void shouldThrowExceptionForZeroPipeLength() {
        PipeProperties invalidProperties = PipeProperties.builder()
                .length(Distance.of(0.0))
                .capacity(Volume.of(500.0))
                .diameter(0.5)
                .roughness(0.0002)
                .build();

        Pipe invalidPipe = createInvalidPipe(invalidProperties);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> FlowValidator.validatePipe(invalidPipe)
        );
        assertEquals("Pipe length must be positive", exception.getMessage());
    }

    private Pipe createInvalidPipe(PipeProperties properties) {
        NetworkNode source = createValidSource();
        NetworkNode destination = createValidDestination();
        return Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();
    }

    private Pipe createTestPipe(NetworkNode source, NetworkNode destination, PipeProperties properties) {
        return Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();
    }
}
