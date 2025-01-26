package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("WaterDemand Tests")
class WaterDemandTest {

    @Test
    @DisplayName("Should create valid WaterDemand")
    void shouldCreateValidWaterDemand() {
        Volume dailyVolume = Volume.of(100);
        WaterDemand waterDemand = WaterDemand.of(dailyVolume);

        assertNotNull(waterDemand);
        assertEquals(dailyVolume, waterDemand.getDailyDemand());
        }

        @Test
        @DisplayName("Should throw ValidationException for null daily demand")
        void shouldThrowValidationExceptionForNullDailyDemand() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> WaterDemand.of(null)
        );
        assertEquals("Daily demand cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Should properly implement equals and hashCode")
        void shouldImplementEqualsAndHashCode() {
        Volume dailyVolume1 = Volume.of(100);
        Volume dailyVolume2 = Volume.of(100);
        Volume dailyVolume3 = Volume.of(200);

        WaterDemand waterDemand1 = WaterDemand.of(dailyVolume1);
        WaterDemand waterDemand2 = WaterDemand.of(dailyVolume2);
        WaterDemand waterDemand3 = WaterDemand.of(dailyVolume3);

        assertEquals(waterDemand1, waterDemand2);
        assertNotEquals(waterDemand1, waterDemand3);
        assertEquals(waterDemand1.hashCode(), waterDemand2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString properly")
    void shouldImplementToString() {
        Volume dailyVolume = Volume.of(100);
        WaterDemand waterDemand = WaterDemand.of(dailyVolume);
        String expected = "WaterDemand(dailyDemand=Volume(cubicMeters=100.0))";
        assertEquals(expected, waterDemand.toString());
    }
}
