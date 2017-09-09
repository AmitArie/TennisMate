package com.tennismate.tennismate.MateMatcher;


import java.io.Serializable;

@SuppressWarnings("serial")
public class MatchingFilter implements Serializable {

    private int radius;
    private double latitude;
    private double longitude;
    //TODO: add more fields


    public MatchingFilter(int radius, double latitude, double longitude) {
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public int getRadius() {
        return radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
