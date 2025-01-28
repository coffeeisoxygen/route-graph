package com.coffeecode.location.elevations.api;

import com.coffeecode.location.elevations.model.Elevation;

public interface ElevationService {
    /**
     * Get elevation for given coordinates
     * 
     * @return Elevation object with source type
     */
    Elevation getElevation(double latitude, double longitude);

    /**
     * Update elevation with manual value
     * 
     * @return New Elevation object with MANUAL type
     */
    Elevation updateElevation(double meters);
}
