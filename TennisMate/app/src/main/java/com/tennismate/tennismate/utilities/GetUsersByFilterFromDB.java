package com.tennismate.tennismate.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.MateMatcher.MatchingFilter;
import com.tennismate.tennismate.user.UserContext;

import java.util.ArrayList;

/**
 * This class is getting an array list, and fills it according to the Matching filter
 * upon getting on doInBackground
 */

public class GetUsersByFilterFromDB {

    private final static String TAG = "GetUsersByFilterFromDB";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGeoLocationRef;
    private GeoFire geoFire;
    private ArrayList<String> mResult;
    private double currentUserLatitude;
    private double currentUserLongitude;
    private double mRadius;


    public GetUsersByFilterFromDB (ArrayList<String> result,
                                   MatchingFilter filter){


        this.mDatabase = FirebaseDatabase.getInstance();
        this.mGeoLocationRef = mDatabase.getReference().child("geo_location");
        this.geoFire = new GeoFire(mGeoLocationRef);
        this.mResult = result;

        this.currentUserLatitude = filter.getLatitude();
        this.currentUserLongitude = filter.getLongitude();
        this.mRadius = filter.getRadius();

    }



    public void execute(){


        final GeoQuery geoQuery = geoFire
                .queryAtLocation(new GeoLocation(
                        this.currentUserLatitude,
                        this.currentUserLongitude), mRadius);

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                Log.e(TAG, "onKeyEntered key:" + key + " location" + location.toString() );
                mResult.add(key);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                Log.e(TAG, "onKeyMoved key:" + key + " location" + location.toString() );

            }

            @Override
            public void onGeoQueryReady() {
                Log.e(TAG, "onGeoQueryReady");
                geoQuery.removeAllListeners();

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e(TAG, "onGeoQueryError: " + error.toString());

            }
        });



    }


}
