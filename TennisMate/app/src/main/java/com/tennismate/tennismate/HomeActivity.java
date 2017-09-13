package com.tennismate.tennismate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.tennismate.tennismate.location.AccessLocation;
import com.tennismate.tennismate.location.LocationSharingBroadcastReceiver;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_find_mates);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (tabId){

                    case R.id.tab_find_mates:
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.tab_mates:
                        MyMatesFragment myMatesFragment = new MyMatesFragment();
                        fragmentTransaction.replace(R.id.fragment_container,myMatesFragment);
                        fragmentTransaction.commit();
                        break;

//                    case R.id.tab_ranking:
//                        RankingFragment rankingFragment = new RankingFragment();
//                        fragmentTransaction.replace(R.id.fragment_container, rankingFragment);
//                        fragmentTransaction.commit();
//                        break;

                    case R.id.tab_profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                        fragmentTransaction.commit();
                        break;
                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
//                Toast.makeText(getApplicationContext(), Integer.toString(tabId), Toast.LENGTH_LONG).show();
            }
        });

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
                    Log.e("Permissions", "ChatUser Approved: ACCESS_COARSE_LOCATION");
                    scheduleAlarm();
                }

                else{
                    Log.e("Permissions", "ChatUser Denied: ACCESS_COARSE_LOCATION");
                    //TODO: Disable functionality

                }
                break;


            case AccessLocation.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == GRANTED){
                    Log.e("Permissions", "ChatUser Approved: ACCESS_FINE_LOCATION");
                    scheduleAlarm();
                }

                else{
                    Log.e("Permissions", "ChatUser Denied: ACCESS_FINE_LOCATION");
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



