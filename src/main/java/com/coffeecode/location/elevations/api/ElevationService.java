package com.coffeecode.location.elevations.api;

import com.coffeecode.location.elevations.model.Elevation;

public interface ElevationService {
    Elevation getElevation(double latitude, double longitude);

    Elevation updateElevation(double meters);
}
