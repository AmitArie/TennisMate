package com.tennismate.tennismate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry2);
    }

    @Override
    protected void onStart(){
        super.onStart();
        int i = 5;
    }
}
