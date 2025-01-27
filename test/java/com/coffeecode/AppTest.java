package com.coffeecode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Application Tests")
class AppTest {

    @Test
    @DisplayName("Should start application without errors")
    void shouldStartApplicationWithoutErrors() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
