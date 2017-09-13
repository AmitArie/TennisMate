package com.tennismate.tennismate.bridge;

import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.user.UserLocation;


public class FromUserContextToDB extends BaseDB {

    private UserContext mUerContext;

    public FromUserContextToDB(UserContext userContext){
        super();
        this.mUerContext = userContext;
    }


    public void execute(){

        final BaseUser baseUser = mUerContext.getUser();
        final UserLocation userLocation = mUerContext.getUserLocation();
        final String token = FirebaseInstanceId.getInstance().getToken();



        DatabaseReference uidQuery =  mUsersRef.child(baseUser.uid);
        final GeoFire geoFire = new GeoFire(mGeoLocationRef);


        uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() == null ){

                    mUsersRef.child(baseUser.uid).setValue(baseUser);
                    mLocationMetaRef.child(baseUser.uid).setValue(userLocation);
                    geoFire.setLocation(baseUser.uid, new GeoLocation(userLocation.latitude, userLocation.longitude));
                    mAccessTokRef.child(baseUser.uid).setValue(token);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }

}
