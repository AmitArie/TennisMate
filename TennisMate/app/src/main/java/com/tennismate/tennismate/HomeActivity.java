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
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        final int GRANTED = PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {

            case AccessLocation.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == GRANTED){
                    Log.e("Permissions", "User Approved: ACCESS_COARSE_LOCATION");
                    scheduleAlarm();
                }

                else{
                    Log.e("Permissions", "User Denied: ACCESS_COARSE_LOCATION");
                    //TODO: Disable functionality

                }
                break;


            case AccessLocation.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == GRANTED){
                    Log.e("Permissions", "User Approved: ACCESS_FINE_LOCATION");
                    scheduleAlarm();
                }

                else{
                    Log.e("Permissions", "User Denied: ACCESS_FINE_LOCATION");
                    //TODO: Disable functionality
                }
                break;

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



