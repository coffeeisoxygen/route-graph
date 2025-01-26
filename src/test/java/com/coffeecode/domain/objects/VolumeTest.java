package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class VolumeTest {

    @Test
    void testVolumeCreationWithPositiveValue() {
        Volume volume = new Volume(5.0);
        assertEquals(5.0, volume.getCubicMeters());
    }

    @Test
    void testVolumeCreationWithZeroValue() {
        Volume volume = new Volume(0.0);
        assertEquals(0.0, volume.getCubicMeters());
    }

    @Test
    void testVolumeCreationWithNegativeValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Volume(-1.0);
        });
        assertEquals("Volume cannot be negative!", exception.getMessage());
    }

    @Test
    void testVolumeEquality() {
        Volume volume1 = new Volume(5.0);
        Volume volume2 = new Volume(5.0);
        assertEquals(volume1, volume2);
    }

    @Test
    void testVolumeInequality() {
        Volume volume1 = new Volume(5.0);
        Volume volume2 = new Volume(10.0);
        assertNotEquals(volume1, volume2);
    }

    @Test
    void testVolumeToString() {
        Volume volume = new Volume(5.0);
        assertEquals("Volume(cubicMeters=5.0)", volume.toString());
    }
}
