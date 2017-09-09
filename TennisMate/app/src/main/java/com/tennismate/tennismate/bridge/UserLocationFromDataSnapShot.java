package com.tennismate.tennismate.bridge;

import com.google.firebase.database.DataSnapshot;
import com.tennismate.tennismate.user.UserLocation;

import java.util.HashMap;

public class UserLocationFromDataSnapShot {


    public static UserLocation create(final Object dataSnapshotValue){

        HashMap ServerUserLocation = (HashMap) dataSnapshotValue;

        if ( ServerUserLocation == null)
            return null;

        String country  =           (String)  ServerUserLocation.get("country");
        String district =           (String)  ServerUserLocation.get("district");
        String street   =           (String)  ServerUserLocation.get("street");
        String lastUpdatedDate =    (String)  ServerUserLocation.get("lastUpdatedDate");

        double latitude  =   ((Number) ServerUserLocation
                .get("latitude")).doubleValue();

        double longitude =  ((Number) ServerUserLocation
                .get("longitude")).doubleValue();

        return new UserLocation(
                latitude,
                longitude,
                country,
                district,
                street,
                lastUpdatedDate
        );
    }
}
