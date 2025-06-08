package com.droute.driverservice.utils;

import java.util.ArrayList;
import java.util.List;

public class InterPolationUtil {

    public static List<LocationPoint> interpolatePoints(
            double sourceLat, double sourceLon,
            double destLat, double destLon,
            int numberOfPoints) {

        List<LocationPoint> points = new ArrayList<>();

        for (int i = 0; i <= numberOfPoints; i++) {
            double fraction = (double) i / numberOfPoints;

            double lat = sourceLat + (destLat - sourceLat) * fraction;
            double lon = sourceLon + (destLon - sourceLon) * fraction;

            points.add(new LocationPoint(lat, lon));
        }

        return points;
    }
}

