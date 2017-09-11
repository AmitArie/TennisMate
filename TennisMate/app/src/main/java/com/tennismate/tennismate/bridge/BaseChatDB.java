package com.tennismate.tennismate.bridge;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseChatDB {

    protected String chatId;
    protected FirebaseDatabase mDatabase;
    protected DatabaseReference mLastMessages;
    protected DatabaseReference mChatUnseenMessages;
    protected DatabaseReference mMessagesRef;

    protected BaseChatDB(String chatId){

        this.chatId = chatId;
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mLastMessages = mDatabase.getReference("chat").child("lastMessages");
        this.mChatUnseenMessages = mDatabase.getReference("chat").child("unseenMessages");
        this.mMessagesRef = mDatabase.getReference("chat").child("messages");
    }
}
