package com.tennismate.tennismate.utilities;


import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.user.UserLocation;

public class SaveUserOnDB {

    private final static String TAG = "SaveUserOnDB_Class";



    public static void firstTime(final UserContext userContext) {

        final BaseUser baseUser = userContext.getUser();
        final UserLocation userLocation = userContext.getUserLocation();

        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();

        final DatabaseReference dbRef = database.getReference();
        final DatabaseReference users = dbRef.child("users");
        final DatabaseReference locationMeta = dbRef.child("location_meta");
        final DatabaseReference geoLocation = dbRef.child("geo_location");

        DatabaseReference uidQuery =  users.child(baseUser.uid);
        final GeoFire geoFire = new GeoFire(geoLocation);


        uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() == null ){

                    users.child(baseUser.uid).setValue(baseUser);
                    locationMeta.child(baseUser.uid).setValue(userLocation);
                    geoFire.setLocation(baseUser.uid, new GeoLocation(userLocation.latitude, userLocation.longitude));


                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                //Log.d(TAG, firebaseError.getMessage());
            }
        });

    }


}

