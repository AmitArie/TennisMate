package com.tennismate.tennismate.bridge;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseDB {

    protected FirebaseDatabase mDatabase;

    protected DatabaseReference mUsersRef;
    protected DatabaseReference mLastMessagesRef;
    protected DatabaseReference mUnseenMessagesRef;
    protected DatabaseReference mMessagesRef;

    protected BaseDB(){


        this.mDatabase = FirebaseDatabase.getInstance();
        this.mUsersRef = mDatabase.getReference("users");
        this.mLastMessagesRef = mDatabase.getReference("chat").child("lastMessages");
        this.mUnseenMessagesRef = mDatabase.getReference("chat").child("unseenMessages");
        this.mMessagesRef = mDatabase.getReference("chat").child("messages");
    }
}
