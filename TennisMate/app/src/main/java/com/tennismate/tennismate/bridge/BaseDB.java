package com.tennismate.tennismate.bridge;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseDB {

    protected FirebaseDatabase mDatabase;


    protected DatabaseReference mUnseenMessagesRef;
    protected DatabaseReference mAccessTokRef;
    protected DatabaseReference mMessagesRef;
    protected DatabaseReference mUsersRef;
    protected DatabaseReference mLastMessagesRef;
    protected DatabaseReference mGeoLocationRef;
    protected DatabaseReference mLocationMetaRef;


    protected BaseDB(){


        this.mDatabase = FirebaseDatabase.getInstance();


        // chat related ref:
        this.mLastMessagesRef = mDatabase.getReference("chat").child("lastMessages");
        this.mUnseenMessagesRef = mDatabase.getReference("chat").child("unseenMessages");
        this.mMessagesRef = mDatabase.getReference("chat").child("messages");

        // users related ref:
        this.mUsersRef = mDatabase.getReference("users");
        this.mAccessTokRef = mDatabase.getReference("accessTok");

        // location related ref:

        this.mGeoLocationRef = mDatabase.getReference("geo_location");
        this.mLocationMetaRef = mDatabase.getReference("location_meta");




    }
}
