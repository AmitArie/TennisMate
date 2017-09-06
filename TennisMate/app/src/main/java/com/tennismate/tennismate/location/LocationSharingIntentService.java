package com.tennismate.tennismate.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.user.User;
import com.tennismate.tennismate.utilities.Time;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationSharingIntentService extends IntentService  {

    public static final String ACTION_SHARE_LOCATION = "ACTION_SHARE_LOCATION";

    private FusedLocationProviderClient mFusedLocationClient;
    Geocoder mGeocoder;






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
                Log.e("L.S.I.S", "Work thread waked up");
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
            Log.e("L.S.I.S", "Got Location:");
            Log.e("L.S.I.S", "latitude:" + String.valueOf(location.getLatitude()));
            Log.e("L.S.I.S", "longitude" + String.valueOf(location.getLongitude()));
            Log.e("L.S.I.S", "accuracy:" + String.valueOf(location.getAccuracy()));

        }
    }

    private void updateUserContext(Location location){

        if( location != null){

            // Getting the user address:

            Address userAddress = getAddress(location);

            // Updating the location of the current user:

            User user = RunTimeSharedData.userContext.getUser();
            user.longitude = location.getLongitude();
            user.latitude = location.getLatitude();

            if( userAddress != null )
            {
                user.country = userAddress.getCountryName();
                user.district = userAddress.getLocality();
                user.street = userAddress.getThoroughfare();
            }

            user.lastUpdatedDate = Time.getFullTime();

            // Updating Firebase db:

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference usersRef = database.getReference();
            final DatabaseReference Users = usersRef.child("users");
            Users.child(user.uid).setValue(user);
        }
    }

    private Address getAddress(Location location){
        List<Address> addresses = null;

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
            Log.e("L.S.I.S", "IO EXCEPTION", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e("L.S.I.S", "IO EXCEPTION", illegalArgumentException);
        }

        return  null;
    }

    private void handleError(){
        Toast.makeText(getApplicationContext(), "Can't get current location." +
                " please add the location permissions", Toast.LENGTH_LONG).show();
        Log.e("L.S.I.S", "Can't get current location.");
    }

}
