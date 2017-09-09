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
import com.tennismate.tennismate.bridge.BasicUserFromDataSnapShot;
import com.tennismate.tennismate.bridge.UserLocationFromDataSnapShot;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.user.UserLocation;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class is getting an array list, and fills it according to the Matching filter
 * upon getting on doInBackground
 */

public class GetUsersByFilterFromDB {

    private final static String TAG = "GetUsersByFilterFromDB";

    private RecyclerAdapter mAdapter;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGeoLocationRef;
    private DatabaseReference mUersRef;
    private DatabaseReference mLocationMeta;
    
    private GeoFire geoFire;
    private ArrayList<String> mGeoFireUids;
    private ArrayList<UserContext> mResult;
    
    private double currentUserLatitude;
    private double currentUserLongitude;
    private double mRadius;



    public GetUsersByFilterFromDB (ArrayList<UserContext> result,
                                   MatchingFilter filter,
                                   RecyclerAdapter adapter){

        this.mAdapter = adapter;
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mGeoLocationRef = mDatabase.getReference().child("geo_location");
        this.mUersRef = mDatabase.getReference().child("users");
        this.mLocationMeta = mDatabase.getReference().child("location_meta");
        
        this.geoFire = new GeoFire(mGeoLocationRef);
        this.mGeoFireUids = new ArrayList<>();
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
                mGeoFireUids.add(key);
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
                
                
                /* Need to fetch data from users, location_meta according to mGeoFireUids*/
                
                for ( String uid : mGeoFireUids ){

                    DatabaseReference basicUserQ    =  mUersRef.child(uid);
                    final DatabaseReference locationMetaQ =  mLocationMeta.child(uid);

                    basicUserQ.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot userDataSnapshot) {

                            final Object userData = userDataSnapshot.getValue();
                            if ( userData == null)
                                return;

                            locationMetaQ.addListenerForSingleValueEvent(new ValueEventListener() {


                                @Override
                                public void onDataChange(DataSnapshot locationDataSnapshot) {

                                    Object locationData = locationDataSnapshot.getValue();
                                    if ( locationData == null)
                                        return;

                                    BaseUser baseUser = BasicUserFromDataSnapShot.create(userData);
                                    UserLocation userLocation = UserLocationFromDataSnapShot.create(locationData);

                                    UserContext userContext = new UserContext(baseUser, userLocation, mAdapter);
                                    mResult.add(userContext);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e(TAG, "onGeoQueryError: " + error.toString());

            }
        });



    }


}
