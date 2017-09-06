package com.tennismate.tennismate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.tennismate.tennismate.location.AccessLocation;
import com.tennismate.tennismate.location.LocationSharingBroadcastReceiver;
import com.tennismate.tennismate.user.UserContext;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    UserContext mUserContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(new HomeFragment(),    "Home");
        viewPagerAdapter.addFragments(new MyMatesFragment(), "My Mates");
        viewPagerAdapter.addFragments(new RankingFragment(), "Ranking");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        checkLocationPerm();


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }




    private void checkLocationPerm(){
        boolean canAccessLocation = AccessLocation.canAccessLocation(this);

        if( ! canAccessLocation){
            Log.i("Permissions", "Requesting Permissions");
            AccessLocation.requestAccess(this);
        }
        else{

            scheduleAlarm();
            /*
                TODO: decide what to do if the user is refusing.
            */
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case AccessLocation.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if ( ! (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.e("Permissions", "User denied");
                    /*

                        TODO: Disable functionality
                        permission denied, boo! Disable the
                        functionality that depends on this permission.
                     */

                }
            }

            case AccessLocation.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if ( ! (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) ) {
                    Log.e("Permissions", "User denied");

                    /*
                        TODO: Disable functionality
                        permission denied, boo! Disable the
                        functionality that depends on this permission.
                     */
                }

            }
        }
    }


    private void scheduleAlarm(){

        Intent intent = new Intent(getApplicationContext(), LocationSharingBroadcastReceiver.class);

        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LocationSharingBroadcastReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1000, pIntent);
    }

}



