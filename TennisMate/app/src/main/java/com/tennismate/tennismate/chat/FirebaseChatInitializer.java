//package com.tennismate.tennismate.chat;
//
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class FirebaseChatInitializer {
//
//    private String chatId;
//    private FirebaseDatabase mDatabase;
//    private DatabaseReference mLastMessagesRef;
//    //private DatabaseReference mMembersRef;
//    private DatabaseReference mMessagesRef;
//
//    public FirebaseChatInitializer (String chatId){
//
//        this.chatId = chatId;
//        this.mDatabase = FirebaseDatabase.getInstance();
//        this.mLastMessagesRef = mDatabase.getReference("chat").child("last_messages");
//        //this.mMembersRef = mDatabase.getReference("chat").child("members");
//        this.mMessagesRef = mDatabase.getReference("chat").child("messages");
//    }
//
//    public void initialize(){
//        mLastMessagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                boolean keyExists = dataSnapshot.hasChild(chatId);
//
//                if( ! keyExists ){
//                    mLastMessagesRef.child(chatId).setValue(null);
//                    //mMembersRef.child(chatId).setValue("");
//                    mMessagesRef.child(chatId).setValue(null);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//}
