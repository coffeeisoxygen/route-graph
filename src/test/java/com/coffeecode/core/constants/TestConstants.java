package com.coffeecode.core.constants;

public class TestConstants {

    // Coordinates
    public static final double JAKARTA_LAT = -6.2088;
    public static final double JAKARTA_LONG = 106.8456;
    public static final double BANDUNG_LAT = -6.9175;
    public static final double BANDUNG_LONG = 107.6191;

    // Edge case coordinates
    public static final double ZERO_LAT = 0.0;
    public static final double ZERO_LONG = 0.0;
    public static final double MAX_LAT = 90.0;
    public static final double MIN_LAT = -90.0;
    public static final double MAX_LONG = 180.0;
    public static final double MIN_LONG = -180.0;

    // Distance calculations
    public static final double ROAD_CORRECTION_FACTOR = 1.155; // Real road distance adjustment
    public static final double JKT_BDG_HAVERSINE = 116.2364; // Direct air distance
    public static final double JKT_BDG_ACTUAL = 116.24; // Actual road distance

    // Special test cases
    public static final double VERY_SHORT_DISTANCE = 0.1; // 100 meters
    public static final double DATE_LINE_LONG_WEST = 179.9;
    public static final double DATE_LINE_LONG_EAST = -179.9;
}
