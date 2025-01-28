package com.coffeecode.location.utils;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.coordinates.impl.GeographicCoordinates;

public class CoordinateUtils {

    private CoordinateUtils() {
        // Prevent instantiation
    }

    /**
     * Converts geographic coordinates (latitude, longitude) to cartesian
     * coordinates.
     *
     * @param geographic The geographic coordinates
     * @return Cartesian coordinates
     */
    public static CartesianCoordinates convertToCartesian(GeographicCoordinates geographic) {
        double radius = 6371e3; // Earth's radius in meters
        double lat = Math.toRadians(geographic.getLatitude());
        double lon = Math.toRadians(geographic.getLongitude());

        double x = radius * Math.cos(lat) * Math.cos(lon);
        double y = radius * Math.cos(lat) * Math.sin(lon);

        return new CartesianCoordinates(x, y);
    }

    /**
     * Converts cartesian coordinates (x, y) back to geographic coordinates
     * (latitude, longitude).
     *
     * @param cartesian The cartesian coordinates
     * @return Geographic coordinates
     */
    public static GeographicCoordinates convertToGeographic(CartesianCoordinates cartesian) {
        double radius = 6371e3; // Earth's radius in meters
        double lat = Math.asin(cartesian.getY() / radius);
        double lon = Math.atan2(cartesian.getY(), cartesian.getX());

        return new GeographicCoordinates(Math.toDegrees(lat), Math.toDegrees(lon));
    }

    /**
     * Checks if a given coordinate is within a specific bounding box.
     *
     * @param coordinate The coordinate to check
     * @param minLat     Minimum latitude
     * @param maxLat     Maximum latitude
     * @param minLon     Minimum longitude
     * @param maxLon     Maximum longitude
     * @return True if the coordinate is within the bounding box, otherwise false
     */
    public static boolean isWithinBoundingBox(GeographicCoordinates coordinate, double minLat, double maxLat,
            double minLon, double maxLon) {
        return coordinate.getLatitude() >= minLat && coordinate.getLatitude() <= maxLat &&
                coordinate.getLongitude() >= minLon && coordinate.getLongitude() <= maxLon;
    }

    /**
     * Rounds a coordinate value to a specified number of decimal places.
     *
     * @param value         The value to round
     * @param decimalPlaces Number of decimal places
     * @return Rounded value
     */
    public static double roundToDecimalPlaces(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }
}
