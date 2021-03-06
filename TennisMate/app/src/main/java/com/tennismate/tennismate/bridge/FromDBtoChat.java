package com.tennismate.tennismate.bridge;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.DBMessage;

public class FromDBtoChat extends BaseDB {
    private static final String TAG = "FromDBtoChat";

    private MessagesListAdapter<ChatMessage> mMessageMessagesListAdapter;
    private final String mActiveUserUid;
    private final String mChatId;

    public FromDBtoChat(String chatId,
                        MessagesListAdapter<ChatMessage> messageMessagesListAdapter
                        ){
        super();
        this.mChatId = chatId;
        this.mMessageMessagesListAdapter = messageMessagesListAdapter;
        this.mActiveUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void loadAllMessages(String chatId){

        Query q = this.mMessagesRef.child(chatId).orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot == null)
                    return;

                Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();

                for( DataSnapshot dataElement: dataSnapshotIterable){

                    DBMessage DBMessage = dataElement.getValue(DBMessage.class);
                    ChatMessage chatMessage = MessagesConverter
                            .fromDbMessageRefTOChatMessage(DBMessage);
                    mMessageMessagesListAdapter.addToStart(chatMessage,true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
        That event is fired once there is a change in the lastMessage real time db,
        Pay attention, every time we add a message to the chat, this event invokes,
        we want to ignore this event if we sent the message, and to pay attention
        only if we received it.
     */
    public void realTimeMessageListener(){

        this.mLastMessagesRef.child(mChatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot == null)
                    return;

                DBMessage lastMessage = dataSnapshot.getValue(DBMessage.class);

                if(lastMessage == null)
                    return;

                if( lastMessage.uid.equals(mActiveUserUid))
                    return;

                ChatMessage chatMessage = MessagesConverter
                        .fromDbMessageRefTOChatMessage(lastMessage);
                mMessageMessagesListAdapter.addToStart(chatMessage, true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

//    private void resetUnreadMessages(){
//        mChatUnseenMessages.child(chatId).child(mActiveUserUid).setValue(0);
//        mUserContext.eraseUnreadMessageCount(chatId);
//    }


}
