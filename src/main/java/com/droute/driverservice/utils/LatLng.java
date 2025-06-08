package com.droute.driverservice.utils;

public  class LatLng {
    public double latitude;
    public double longitude;

    public LatLng(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }
}
