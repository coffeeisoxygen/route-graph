package com.coffeecode.calculation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HeadLoss Factory Tests")
class HeadLossFactoryTest {
    private HeadLossFactory factory;
    private TestConfig config;

    @BeforeEach
    void setUp() {
        config = new TestConfig();
        factory = new HeadLossFactory(config);
    }

    @Test
    @DisplayName("Should create DarcyWeisbach calculator")
    void shouldCreateCalculator() {
        // When
        HeadLossCalculator calculator = factory.createCalculator();

        // Then
        assertNotNull(calculator);
        assertTrue(calculator instanceof DarcyWeisbachCalculator);
    }

    @Test
    @DisplayName("Calculator should use config values")
    void calculatorShouldUseConfigValues() {
        // Given
        TestConfig customConfig = new TestConfig(2.0, 9.81);
        factory = new HeadLossFactory(customConfig);

        // When
        HeadLossCalculator calculator = factory.createCalculator();

        // Then
        assertNotNull(calculator);
    }
}
