package com.tennismate.tennismate.utilities;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.user.UserLocation;

import java.util.HashMap;

public class GetUserFromDB {

    private UserContext mUserContext;


    public GetUserFromDB(FirebaseUser fireBaseUser){

        final String uid = fireBaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference usersRef =     database.getReference("users");
        DatabaseReference locationMeta = database.getReference("location_meta");

        DatabaseReference uidQuery =            usersRef.child(uid);
        final DatabaseReference locationMetaQuery =   locationMeta.child(uid);

        uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap ServerUser = (HashMap) dataSnapshot.getValue();

                if ( ServerUser == null)
                    return;

                String firstName = (String) ServerUser.get("firstName");
                String lastName = (String) ServerUser.get("lastName");
                String email = (String) ServerUser.get("email");
                String level = (String)  ServerUser.get("level");
                String photoUrl = (String) ServerUser.get("photoUrl");

                final BaseUser baseUser = new BaseUser(
                        uid,
                        firstName,
                        lastName,
                        email,
                        level,
                        photoUrl);


                locationMetaQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HashMap ServerUserLocation = (HashMap) dataSnapshot.getValue();

                        if ( ServerUserLocation == null)
                            return;

                        String country  =           (String)  ServerUserLocation.get("country");
                        String district =           (String)  ServerUserLocation.get("district");
                        String street   =           (String)  ServerUserLocation.get("street");
                        String lastUpdatedDate =    (String)  ServerUserLocation.get("lastUpdatedDate");

                        double latitude  =   ((Number) ServerUserLocation
                                .get("latitude")).doubleValue();

                        double longitude =  ((Number) ServerUserLocation
                                .get("longitude")).doubleValue();

                        UserLocation userLocation = new UserLocation(
                                latitude,
                                longitude,
                                country,
                                district,
                                street,
                                lastUpdatedDate
                        );

                        RunTimeSharedData.setUserContext(new UserContext(baseUser, userLocation));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("User", firebaseError.getMessage());
            }
        });

    }

}
