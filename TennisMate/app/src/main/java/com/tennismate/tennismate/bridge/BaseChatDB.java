package com.tennismate.tennismate.bridge;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseChatDB {

    protected String chatId;
    protected FirebaseDatabase mDatabase;
    protected DatabaseReference mLastMessages;
    //protected DatabaseReference mMembersRef;
    protected DatabaseReference mMessagesRef;

    protected BaseChatDB(String chatId){

        this.chatId = chatId;
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mLastMessages = mDatabase.getReference("chat").child("lastMessages");
        //this.mMembersRef = mDatabase.getReference("chat").child("members");
        this.mMessagesRef = mDatabase.getReference("chat").child("messages");
    }
}
