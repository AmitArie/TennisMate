package com.tennismate.tennismate.bridge;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.ChatUser;
import com.tennismate.tennismate.chat.DBMessage;
import com.tennismate.tennismate.mates.Dialog;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.user.UserLocation;
import com.tennismate.tennismate.utilities.ChatIdGenerator;
import com.tennismate.tennismate.utilities.ImageDownloader;

import java.util.ArrayList;

public class FromDBToUserContext {

    public static final String TAG = "FromDBToUserContext";

    private FirebaseUser mFirebaseUser;
    private DatabaseReference mUsersRef;
    private DatabaseReference mLocationMetaRef;
    private DatabaseReference mLastMessagesRef;
    protected DatabaseReference mChatUnseenMessages;
    private UserContext mUserContext;
    private String mUid;


    public FromDBToUserContext(UserContext userContext) {

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUid = mFirebaseUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mUsersRef = database.getReference("users");
        mLocationMetaRef = database.getReference("location_meta");
        mLastMessagesRef = database.getReference("chat").child("lastMessages");
        mChatUnseenMessages = database.getReference("chat").child("unseenMessages");

        mUserContext = userContext;
        mUserContext.setDialogs(new ArrayList<Dialog>());


    }

    public void fetchBaseUser() {

        mUsersRef.child(mUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null)
                    return;

                BaseUser baseUser = dataSnapshot.getValue(BaseUser.class);
                mUserContext.setUser(baseUser);
                new ImageDownloader(mUserContext)
                        .execute(baseUser.photoUrl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fetchUserLocation() {

        mLocationMetaRef.child(mUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null)
                    return;

                UserLocation userLocation = dataSnapshot.getValue(UserLocation.class);
                mUserContext.setUserLocation(userLocation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }







}
