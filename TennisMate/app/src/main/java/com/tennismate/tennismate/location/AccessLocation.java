package com.tennismate.tennismate.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class AccessLocation
{
    final public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    final public static int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2;

    public static boolean canAccessLocation(Activity activity)
    {

        return PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION) &&

                PackageManager.PERMISSION_GRANTED ==
                        ContextCompat.checkSelfPermission(activity,
                                Manifest.permission.ACCESS_FINE_LOCATION);

    }

    public static void requestAccess(Activity activity){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
    }


}
