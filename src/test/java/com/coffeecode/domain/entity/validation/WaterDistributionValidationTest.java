package com.coffeecode.domain.entity.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entity.NetworkNode;
import com.coffeecode.domain.entity.WaterSource;
import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Distance;
import com.coffeecode.domain.objects.PipeProperties;
import com.coffeecode.domain.objects.Volume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Water Distribution Validation Tests")
class WaterDistributionValidationTest {

    @Test
    @DisplayName("Should validate node connection")
    void shouldValidateNodeConnection() {
        NetworkNode source = createTestSource();
        NetworkNode destination = createTestSource();
        PipeProperties properties = createTestProperties();

        assertThrows(ValidationException.class,
                () -> WaterDistributionValidation.validateConnection(source, source, properties),
                "Should not allow connection to self");

        assertThrows(ValidationException.class,
                () -> WaterDistributionValidation.validateConnection(null, destination, properties),
                "Should not allow null source");

        assertThrows(ValidationException.class,
                () -> WaterDistributionValidation.validateConnection(source, null, properties),
                "Should not allow null destination");

        assertThrows(ValidationException.class,
                () -> WaterDistributionValidation.validateConnection(source, destination, null),
                "Should not allow null properties");
    }

    private WaterSource createTestSource() {
        return WaterSource.builder()
                .name("Test Source")
                .location(Coordinate.of(0, 0))
                .capacity(Volume.of(1000))
                .build();
    }

    private PipeProperties createTestProperties() {
        return PipeProperties.builder()
                .length(Distance.of(100))
                .capacity(Volume.of(500))
                .build();
    }
}
