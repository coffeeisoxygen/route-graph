package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WaterDemand Tests")
class WaterDemandTest {

    @Test
    @DisplayName("Should create valid WaterDemand")
    void shouldCreateValidWaterDemand() {
        Volume dailyVolume = new Volume(100);
        WaterDemand waterDemand = new WaterDemand(dailyVolume);

        assertNotNull(waterDemand);
        assertEquals(dailyVolume, waterDemand.getDailyDemand());
    }

    @Test
    @DisplayName("Should throw exception for null daily demand")
    void shouldThrowExceptionForNullDailyDemand() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new WaterDemand(null)
        );
        assertEquals("Daily demand cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("Should properly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        Volume dailyVolume1 = new Volume(100);
        Volume dailyVolume2 = new Volume(100);
        Volume dailyVolume3 = new Volume(200);

        WaterDemand waterDemand1 = new WaterDemand(dailyVolume1);
        WaterDemand waterDemand2 = new WaterDemand(dailyVolume2);
        WaterDemand waterDemand3 = new WaterDemand(dailyVolume3);

        assertEquals(waterDemand1, waterDemand2);
        assertNotEquals(waterDemand1, waterDemand3);
        assertEquals(waterDemand1.hashCode(), waterDemand2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString properly")
    void shouldImplementToString() {
        Volume dailyVolume = new Volume(100);
        WaterDemand waterDemand = new WaterDemand(dailyVolume);
        String expected = "WaterDemand(dailyDemand=Volume(cubicMeters=100.0))";
        assertEquals(expected, waterDemand.toString());
    }
}
