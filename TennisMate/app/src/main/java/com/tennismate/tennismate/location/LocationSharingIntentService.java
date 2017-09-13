package com.tennismate.tennismate.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;


import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tennismate.tennismate.user.UserLocation;
import com.tennismate.tennismate.utilities.Time;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class LocationSharingIntentService extends IntentService  {

    public static final String ACTION_SHARE_LOCATION = "ACTION_SHARE_LOCATION";
    private static String TAG = "L.S.I.S";
    private FusedLocationProviderClient mFusedLocationClient;
    private Geocoder mGeocoder;


    public LocationSharingIntentService() {
        super("LocationSharingIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mGeocoder = new Geocoder(this, Locale.getDefault());

    }

    /*
            This method is invoked on the worker thread with a request to process.
         */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (action.equals(ACTION_SHARE_LOCATION)) {
        //        Log.e(TAG, "Work thread waked up");
                shareLocation();
            }
        }
    }

    private void shareLocation() {
        try{

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if( location == null)
                                return;

                            LocationLog(location);
                            updateUserContext(location);

                        }
                    });
        }
        catch(SecurityException e){
            handleError();
        }

    }



    private void LocationLog(Location location){

        if( location != null){
       //     Log.e(TAG, "Got Location:");
       //     Log.e(TAG, "latitude:" + String.valueOf(location.getLatitude()));
      //      Log.e(TAG, "longitude" + String.valueOf(location.getLongitude()));
      //      Log.e(TAG, "accuracy:" + String.valueOf(location.getAccuracy()));

        }
    }

    private void updateUserContext(Location location){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if( firebaseAuth == null){
      //      Log.e(TAG, "LocationSharing Intent Service: line 108. FirebaseAuth is null.");
            return;
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if( firebaseUser == null){
       //     Log.e(TAG, "LocationSharing Intent Service: line 115. firebaseUser is null.");
            return;
        }

        String uid = firebaseUser.getUid();

        if ( uid == null){
       //     Log.e(TAG, "LocationSharing Intent Service: line 122. uid is null.");
            return;
        }

        UserLocation userLocation = new UserLocation();


        // Getting the user address:

        Address userAddress = getAddress(location);

        // Updating the location of the current user:

        userLocation.longitude = location.getLongitude();
        userLocation.latitude = location.getLatitude();

        if( userAddress != null )
        {
            userLocation.country = userAddress.getCountryName();
            userLocation.district = userAddress.getLocality();
            userLocation.street = userAddress.getThoroughfare();
        }

        userLocation.mLastUpdatedDate = Time.getFullTime();



        // Updating geo_location schema:

        DatabaseReference geoLocationRef = FirebaseDatabase.getInstance().getReference("geo_location");
        GeoFire geoFire = new GeoFire(geoLocationRef);

        geoFire.setLocation(uid , new GeoLocation( userLocation.latitude , userLocation.longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
         //           Log.e(TAG, "There was an error saving the location to GeoFire: " + error);
                } else {
         //           Log.e(TAG,"Location saved on server successfully!");
                }
            }
        });

        // Updating meta_location:


        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();

        final DatabaseReference locationMetaRef = database.getReference().child("location_meta");
        locationMetaRef.child(uid).setValue(userLocation);

    }

    private Address getAddress(Location location){
        List<Address> addresses;

        try {
            addresses = mGeocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);

            if( 0 < addresses.size() ) {
                return addresses.get(0);
            }

        } catch (IOException ioException) {
            // Catch network or other I/O problems.
     //       Log.e(TAG, "IO EXCEPTION", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
     //       Log.e(TAG, "IllegalArgumentException", illegalArgumentException);
        }

        return  null;
    }

    private void handleError(){
        Toast.makeText(getApplicationContext(), "Can't get current location." +
                " please add the location permissions", Toast.LENGTH_LONG).show();
    //    Log.e(TAG, "Can't get current location.");
    }

}
