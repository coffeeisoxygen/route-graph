package com.coffeecode.core.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import java.util.UUID;
import com.coffeecode.core.util.CoordinateValidator;

public class LocationTest {

    @Test
    public void testCreateNew_ValidLocation() {
        // Arrange
        String name = "Test Location";
        double longitude = 100.0;
        double latitude = 50.0;
        Mockito.mockStatic(CoordinateValidator.class);
        Mockito.when(CoordinateValidator.isValidCoordinate(latitude, longitude)).thenReturn(true);

        // Act
        Location location = Location.createNew(name, longitude, latitude);

        // Assert
        Assertions.assertNotNull(location.getIdLocation());
        Assertions.assertEquals(name, location.getName());
        Assertions.assertEquals(longitude, location.getLongitude());
        Assertions.assertEquals(latitude, location.getLatitude());
    }

    @Test
    public void testCreateNew_InvalidName() {
        // Arrange
        String name = "";
        double longitude = 100.0;
        double latitude = 50.0;

        // Act & Assert
        Executable executable = () -> Location.createNew(name, longitude, latitude);
        Assertions.assertThrows(IllegalArgumentException.class, executable, "Name cannot be null or empty");
    }

    @Test
    public void testCreateNew_InvalidCoordinates() {
        // Arrange
        String name = "Test Location";
        double longitude = 200.0;
        double latitude = 100.0;
        Mockito.mockStatic(CoordinateValidator.class);
        Mockito.when(CoordinateValidator.isValidCoordinate(latitude, longitude)).thenReturn(false);

        // Act & Assert
        Executable executable = () -> Location.createNew(name, longitude, latitude);
        Assertions.assertThrows(IllegalArgumentException.class, executable, "Invalid latitude or longitude");
    }
}
