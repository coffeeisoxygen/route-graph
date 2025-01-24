package com.coffeecode.core.util;

public class CoordinateValidator {

    private CoordinateValidator() {
        // Private constructor to prevent instantiation
    }

    // Method to validate latitude
    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    // Method to validate longitude
    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

    // Method to validate both latitude and longitude
    public static boolean isValidCoordinate(double latitude, double longitude) {
        return isValidLatitude(latitude) && isValidLongitude(longitude);
    }
}
