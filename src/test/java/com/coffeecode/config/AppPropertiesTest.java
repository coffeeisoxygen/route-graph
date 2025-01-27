package com.coffeecode.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

@DisplayName("AppProperties Tests")
class AppPropertiesTest {

    private AppProperties appProperties;

    @BeforeEach
    public void setUp() {
        appProperties = AppProperties.getInstance();
    }

    @Test
    @DisplayName("Should get same instance (Singleton)")
    void shouldGetSameInstance() {
        // When
        AppProperties instance1 = AppProperties.getInstance();
        AppProperties instance2 = AppProperties.getInstance();

        // Then
        assertSame(instance1, instance2);
    }

    @Test
    @DisplayName("Should load water properties")
    void shouldLoadWaterProperties() {
        // Given
        AppProperties props = AppProperties.getInstance();

        // When
        double velocity = props.getDouble("water.velocity.default");
        double viscosity = props.getDouble("water.kinematic.viscosity");

        // Then
        assertEquals(1.5, velocity, 0.001);
        assertEquals(1.004E-6, viscosity, 0.000001);
    }

    @Test
    @DisplayName("Should throw exception for missing property")
    void shouldThrowExceptionForMissingProperty() {
        // Given
        AppProperties props = AppProperties.getInstance();

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> props.getDouble("non.existent.property"));
        assertEquals("Property not found: non.existent.property", exception.getMessage());
    }

    @Test
    @DisplayName("Should load environment properties")
    void shouldLoadEnvironmentProperties() {
        // Given
        AppProperties props = AppProperties.getInstance();

        // When
        double gravity = props.getDouble("environment.gravity");
        double pressure = props.getDouble("environment.pressure.atmospheric");

        // Then
        assertEquals(9.81, gravity, 0.001);
        assertEquals(101325, pressure, 0.001);
    }

    @Test
    void testGetDoublePropertyNotExists() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            appProperties.getDouble("nonexistent.key");
        });
        assertEquals("Property not found: nonexistent.key", exception.getMessage());
    }

    @Test
    void testLoadPropertiesFileNotFound() {
        try (MockedStatic<AppProperties> mockedStatic = Mockito.mockStatic(AppProperties.class)) {
            mockedStatic.when(AppProperties::getInstance)
                    .thenThrow(new IllegalStateException("Cannot find application.properties"));

            Exception exception = assertThrows(IllegalStateException.class, AppProperties::getInstance);
            assertEquals("Cannot find application.properties", exception.getMessage());
        }
    }

    @Test
    void testLoadPropertiesIOException() {
        try (MockedStatic<AppProperties> mockedStatic = Mockito.mockStatic(AppProperties.class)) {
            mockedStatic.when(AppProperties::getInstance)
                    .thenThrow(new IllegalStateException("Failed to load properties"));

            Exception exception = assertThrows(IllegalStateException.class, AppProperties::getInstance);
            assertEquals("Failed to load properties", exception.getMessage());
        }
    }
}
