package com.coffeecode.domain.values.pipe;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.constants.MaterialConstants;

@DisplayName("PipeMaterial Tests")
class PipeMaterialTest {
    @Test
    @DisplayName("Should have correct material properties")
    void shouldHaveCorrectProperties() {
        assertAll(
                () -> assertEquals(MaterialConstants.Roughness.PVC, PipeMaterial.PVC.getRoughness()),
                () -> assertEquals(0.05, PipeMaterial.PVC.getMinDiameter()),
                () -> assertEquals(0.5, PipeMaterial.PVC.getMaxDiameter()));
    }
}
