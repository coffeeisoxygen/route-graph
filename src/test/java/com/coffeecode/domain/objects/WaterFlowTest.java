package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WaterFlow Tests")
class WaterFlowTest {

    @Test
    @DisplayName("Should create valid WaterFlow")
    void shouldCreateValidWaterFlow() {
        WaterFlow waterFlow = new WaterFlow(5.0);

        assertNotNull(waterFlow);
        assertEquals(5.0, waterFlow.getFlowRate());
    }

    @Test
    @DisplayName("Should throw exception for negative flow rate")
    void shouldThrowExceptionForNegativeFlowRate() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WaterFlow(-1.0)
        );
        assertEquals("Flow rate cannot be negative!", exception.getMessage());
    }

    @Test
    @DisplayName("Should properly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        WaterFlow waterFlow1 = new WaterFlow(5.0);
        WaterFlow waterFlow2 = new WaterFlow(5.0);
        WaterFlow waterFlow3 = new WaterFlow(10.0);

        assertEquals(waterFlow1, waterFlow2);
        assertNotEquals(waterFlow1, waterFlow3);
        assertEquals(waterFlow1.hashCode(), waterFlow2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString properly")
    void shouldImplementToString() {
        WaterFlow waterFlow = new WaterFlow(5.0);
        String expected = "WaterFlow(flowRate=5.0)";
        assertEquals(expected, waterFlow.toString());
    }
}
