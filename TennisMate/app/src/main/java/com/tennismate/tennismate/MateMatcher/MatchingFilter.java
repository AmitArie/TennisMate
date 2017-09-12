package com.tennismate.tennismate.MateMatcher;


import java.io.Serializable;

@SuppressWarnings("serial")
public class MatchingFilter implements Serializable {

    public  int radius;
    public  double latitude;
    public  double longitude;
    //TODO: add more fields


    public MatchingFilter(int radius, double latitude, double longitude) {
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
