package com.droute.driverservice.utils;

import java.util.ArrayList;
import java.util.List;

public class PolylineDecoder {
    public static List<LatLng> decode(final String encodedPath) {
        int len = encodedPath.length();
        final List<LatLng> path = new ArrayList<>();
        int index = 0, lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;

            do {
                b = encodedPath.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int deltaLat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += deltaLat;

            shift = 0;
            result = 0;
            do {
                b = encodedPath.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int deltaLng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += deltaLng;

            path.add(new LatLng(lat / 1E5, lng / 1E5));
        }

        return path;
    }
}


