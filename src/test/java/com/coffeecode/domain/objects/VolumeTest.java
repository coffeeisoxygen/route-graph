package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Volume Tests")
class VolumeTest {

    @Test
    @DisplayName("Should create volume with positive value")
    void testVolumeCreationWithPositiveValue() {
        Volume volume = Volume.of(5.0);
        assertEquals(5.0, volume.getCubicMeters());
        }

        @Test
        @DisplayName("Should create volume with zero value")
        void testVolumeCreationWithZeroValue() {
        Volume volume = Volume.of(0.0);
        assertEquals(0.0, volume.getCubicMeters());
    }

    @Test
    @DisplayName("Should throw ValidationException for negative volume")
    void testVolumeCreationWithNegativeValue() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            Volume.of(-1.0);
        });
        assertEquals("Volume cannot be negative!", exception.getMessage());
    }

    @Test
    @DisplayName("Should properly implement equals and hashCode")
    void testVolumeEquality() {
        Volume volume1 = Volume.of(5.0);
        Volume volume2 = Volume.of(5.0);
        assertEquals(volume1, volume2);
    }

    @Test
    @DisplayName("Should properly implement equals and hashCode")
    void testVolumeInequality() {
        Volume volume1 = Volume.of(5.0);
        Volume volume2 = Volume.of(10.0);
        assertNotEquals(volume1, volume2);
    }

    @Test
    @DisplayName("Should implement toString properly")
    void testVolumeToString() {
        Volume volume = Volume.of(5.0);
        assertEquals("Volume(cubicMeters=5.0)", volume.toString());
    }
}
