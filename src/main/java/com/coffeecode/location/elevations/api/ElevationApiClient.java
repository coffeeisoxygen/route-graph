package com.coffeecode.location.elevations.api;

import com.coffeecode.location.elevations.exception.ElevationException;

public interface ElevationApiClient {
    String getElevationData(double latitude, double longitude) throws ElevationException;
}
