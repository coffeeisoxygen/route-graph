package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Volume;

@DisplayName("WaterSource Tests")
class WaterSourceTest {

    @Test
    @DisplayName("Should create WaterSource with valid parameters")
    void shouldCreateWaterSourceWithValidParameters() {
        // Arrange
        String name = "Source1";
        Coordinate location = Coordinate.of(0.0, 0.0);
        Volume capacity = new Volume(1000.0);

        // Act
        WaterSource source = new WaterSource(name, location, capacity);

        // Assert
        assertNotNull(source.getId());
        assertEquals(name, source.getName());
        assertEquals(location, source.getLocation());
        assertEquals(capacity, source.getCapacity());
        assertEquals(NetworkNode.NodeType.SOURCE, source.getType());
    }

    @Test
    @DisplayName("Should have unique IDs for different instances")
    void shouldHaveUniqueIds() {
        // Arrange
        WaterSource source1 = new WaterSource("Source1",
                Coordinate.of(0.0, 0.0),
                new Volume(1000.0));
        WaterSource source2 = new WaterSource("Source2",
                Coordinate.of(0.0, 0.0),
                new Volume(1000.0));

        // Assert
        assertNotEquals(source1.getId(), source2.getId());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToStringCorrectly() {
        // Arrange
        WaterSource source = new WaterSource("Source1",
                Coordinate.of(0.0, 0.0),
                new Volume(1000.0));

        // Act
        String toString = source.toString();

        // Assert
        assertTrue(toString.contains("Source1"));
        assertTrue(toString.contains("SOURCE"));
        assertTrue(toString.contains("1000.0"));
    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        Coordinate location = Coordinate.of(0.0, 0.0);
        Volume capacity = new Volume(1000.0);
        WaterSource source1 = new WaterSource("Source1", location, capacity);
        WaterSource source2 = new WaterSource("Source1", location, capacity);

        // Assert
        assertNotEquals(source1, source2); // Should not be equal due to different UUIDs
        assertNotEquals(source1.hashCode(), source2.hashCode());
    }
}
