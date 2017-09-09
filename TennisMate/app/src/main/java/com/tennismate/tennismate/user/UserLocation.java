package com.tennismate.tennismate.user;


import com.tennismate.tennismate.utilities.Time;

public class UserLocation {

    public double latitude;
    public double longitude;
    public String country;
    public String district;
    public String street;
    public String mLastUpdatedDate;

    public UserLocation(){
        this.latitude = 0.1;
        this.longitude = 0.1;
        this.country = "";
        this.district = "";
        this.street = "";
        this.mLastUpdatedDate = Time.getFullTime();
    }

    public UserLocation(double latitude, double longitude, String country,
                        String district, String street, String lastUpdatedDate) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.district = district;
        this.street = street;
        this.mLastUpdatedDate = lastUpdatedDate;
    }
}
