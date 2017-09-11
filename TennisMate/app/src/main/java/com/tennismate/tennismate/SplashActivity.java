package com.tennismate.tennismate;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.utilities.GetUserFromDB;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {

        super.onStart();
        RunTimeSharedData.fetchUserData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME);
    }
}
