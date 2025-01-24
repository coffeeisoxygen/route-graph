package com.coffeecode.core.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.coffeecode.core.constants.TestConstants;

@DisplayName("Location Entity Tests")
class LocationTest {

    private Location testLocation;

    @BeforeEach
    void setUp() {
        testLocation = Location.createNew("Test Location", TestConstants.JAKARTA_LONG, TestConstants.JAKARTA_LAT);
    }

    @Nested
    @DisplayName("Location Creation Tests")
    class LocationCreationTests {

        @Test
        @DisplayName("Should create location with valid data")
        void testCreateNew_ValidLocation() {
            assertAll(
                    () -> assertNotNull(testLocation.getIdLocation()),
                    () -> assertEquals("Test Location", testLocation.getName()),
                    () -> assertEquals(TestConstants.JAKARTA_LONG, testLocation.getLongitude()),
                    () -> assertEquals(TestConstants.JAKARTA_LAT, testLocation.getLatitude())
            );
        }

        @Test
        @DisplayName("Should create with valid coordinates")
        void testCreateNew_ValidCoordinates() {
            // Act
            Location location = Location.createNew("Test",
                    TestConstants.JAKARTA_LONG,
                    TestConstants.JAKARTA_LAT);

            // Assert
            assertAll(
                    () -> assertNotNull(location.getIdLocation()),
                    () -> assertEquals("Test", location.getName()),
                    () -> assertEquals(TestConstants.JAKARTA_LONG, location.getLongitude()),
                    () -> assertEquals(TestConstants.JAKARTA_LAT, location.getLatitude())
            );
        }

        @ParameterizedTest
        @CsvSource({
            "200.0, 0.0",
            "0.0, 100.0",
            "181.0, 91.0"
        })
        @DisplayName("Should reject invalid coordinates")
        void testCreateNew_InvalidCoordinates(double longitude, double latitude) {
            assertThrows(IllegalArgumentException.class,
                    () -> Location.createNew("Test", longitude, latitude));
        }
    }

    @Nested
    @DisplayName("Location Validation Tests")
    class LocationValidationTests {

        @Test
        @DisplayName("Should reject null or empty name")
        void testCreateNew_InvalidName() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> Location.createNew(null, TestConstants.JAKARTA_LONG, TestConstants.JAKARTA_LAT)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> Location.createNew("", TestConstants.JAKARTA_LONG, TestConstants.JAKARTA_LAT))
            );
        }

        @Test
        @DisplayName("Should accept boundary coordinates")
        void testCreateNew_BoundaryCoordinates() {
            assertAll(
                    () -> assertDoesNotThrow(() -> Location.createNew("Max", TestConstants.MAX_LONG, TestConstants.MAX_LAT)),
                    () -> assertDoesNotThrow(() -> Location.createNew("Min", TestConstants.MIN_LONG, TestConstants.MIN_LAT)),
                    () -> assertDoesNotThrow(() -> Location.createNew("Zero", TestConstants.ZERO_LONG, TestConstants.ZERO_LAT))
            );
        }
    }

    @Nested
    @DisplayName("Location Identity Tests")
    class LocationIdentityTests {

        @Test
        @DisplayName("Should create unique IDs for same coordinates")
        void testCreateNew_SameCoordinates() {
            Location loc1 = Location.createNew("Location1", 100.0, 50.0);
            Location loc2 = Location.createNew("Location2", 100.0, 50.0);

            assertAll(
                    () -> assertNotEquals(loc1.getIdLocation(), loc2.getIdLocation()),
                    () -> assertEquals(loc1.getLongitude(), loc2.getLongitude()),
                    () -> assertEquals(loc1.getLatitude(), loc2.getLatitude())
            );
        }

        @Test
        @DisplayName("Should handle special characters in name")
        void testCreateNew_SpecialCharacterName() {
            String specialName = "Location #1 & (2)";
            Location location = Location.createNew(specialName, 100.0, 50.0);
            assertEquals(specialName, location.getName());
        }
    }

    @Nested
    @DisplayName("Location Advanced Tests")
    class LocationAdvancedTests {

        @Test
        @DisplayName("Should handle UTF-8 names")
        void testCreateNew_UTF8Names() {
            String utf8Name = "Локация";
            Location location = Location.createNew(utf8Name,
                    TestConstants.JAKARTA_LONG,
                    TestConstants.JAKARTA_LAT);
            assertEquals(utf8Name, location.getName());
        }

        @Test
        @DisplayName("Should handle very long names")
        void testCreateNew_LongName() {
            String longName = "A".repeat(255);
            Location location = Location.createNew(longName,
                    TestConstants.JAKARTA_LONG,
                    TestConstants.JAKARTA_LAT);
            assertEquals(longName, location.getName());
        }

        @Test
        @DisplayName("Should preserve coordinate precision")
        void testCreateNew_CoordinatePrecision() {
            double preciseLong = 106.84558237489;
            double preciseLat = -6.20880123456;

            Location location = Location.createNew("Precise", preciseLong, preciseLat);

            assertEquals(preciseLong, location.getLongitude(), 0.00000000001);
            assertEquals(preciseLat, location.getLatitude(), 0.00000000001);
        }
    }
}
