package com.coffeecode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeoNode extends Node {
    private double latitude;
    private double longitude;

    public GeoNode(double latitude, double longitude, double elevation, double velocity, double pressure) {
        super(latitude, longitude, elevation, velocity, pressure);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getDistanceTo(Node other) {
        if (!(other instanceof GeoNode)) {
            return super.getDistanceTo(other);
        }
        GeoNode geoOther = (GeoNode) other;
        return calculateHaversineDistance(this, geoOther);
    }

    private static double calculateHaversineDistance(GeoNode point1, GeoNode point2) {
        final double R = 6371e3; // Earth's radius in meters (changed from km)

        double lat1 = Math.toRadians(point1.getLatitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double deltaLat = Math.toRadians(point2.getLatitude() - point1.getLatitude());
        double deltaLon = Math.toRadians(point2.getLongitude() - point1.getLongitude());

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Returns distance in meters
    }
}
