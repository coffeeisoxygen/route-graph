package com.coffeecode.location.coordinates.impl;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.coordinates.constants.EarthConstants;
import com.coffeecode.location.coordinates.constants.MapBoundaryConstants;

import lombok.Value;

@Value
public class GeographicCoordinates implements Coordinates {
    double latitude;
    double longitude;

    public GeographicCoordinates(double latitude, double longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validateLatitude(double latitude) {
        if (latitude < MapBoundaryConstants.MIN_LATITUDE ||
                latitude > MapBoundaryConstants.MAX_LATITUDE) {
            throw new IllegalArgumentException(
                    String.format("Latitude must be between %.1f° and %.1f°",
                            MapBoundaryConstants.MIN_LATITUDE,
                            MapBoundaryConstants.MAX_LATITUDE));
        }
    }

    private void validateLongitude(double longitude) {
        if (longitude < MapBoundaryConstants.MIN_LONGITUDE ||
                longitude > MapBoundaryConstants.MAX_LONGITUDE) {
            throw new IllegalArgumentException(
                    String.format("Longitude must be between %.1f° and %.1f°",
                            MapBoundaryConstants.MIN_LONGITUDE,
                            MapBoundaryConstants.MAX_LONGITUDE));
        }
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        if (!(other instanceof GeographicCoordinates)) {
            throw new IllegalArgumentException(
                    String.format("Cannot calculate distance between %s and %s",
                            this.getClass().getSimpleName(),
                            other != null ? other.getClass().getSimpleName() : "null"));
        }
        return calculateHaversineDistance(this, (GeographicCoordinates) other);
    }

    private static double calculateHaversineDistance(GeographicCoordinates point1, GeographicCoordinates point2) {
        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EarthConstants.EARTH_RADIUS_KM * c; // Return in kilometers
    }
}
