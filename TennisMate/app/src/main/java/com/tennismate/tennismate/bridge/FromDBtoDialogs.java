package com.tennismate.tennismate.bridge;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.ChatUser;
import com.tennismate.tennismate.chat.DBMessage;
import com.tennismate.tennismate.mates.Dialog;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.utilities.ChatIdGenerator;

import java.util.ArrayList;

public class FromDBtoDialogs extends BaseDB {

    private static final String TAG = "FromDBtoChat";
    private DialogsListAdapter<Dialog> mDialogDialogsListAdapter;
    private String mACtiveUserUid;

    public FromDBtoDialogs (DialogsListAdapter<Dialog> dialogsAdapter){


        this.mDialogDialogsListAdapter = dialogsAdapter;
        this.mACtiveUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fetchRealTimeDialogs();
    }


    public void fetchRealTimeDialogs() {

        mLastMessagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot == null)
                    return;

                if (dataSnapshot.getKey() == null)
                    return;

                final String chatId = dataSnapshot.getKey();
                if ( !chatId.contains(mACtiveUserUid) )
                    return;

                // extracting the message:
                DBMessage dbMessage = dataSnapshot.getValue(DBMessage.class);
                final ChatMessage lastMessage = MessagesConverter
                        .fromDbMessageRefTOChatMessage(dbMessage);

                // fetching information about the other user:
                String otherUid = ChatIdGenerator.extractOtherId(chatId, mACtiveUserUid);
                mUsersRef.child(otherUid).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot == null)
                            return;
                        final BaseUser baseUser = dataSnapshot.getValue(BaseUser.class);

                        final ArrayList<ChatUser> users = new ArrayList<>();
                        users.add(lastMessage.getUser());

                        // Getting count of unseenMessages:

                        final DatabaseReference r =
                                mUnseenMessagesRef.child(chatId).child(mACtiveUserUid);

                        r.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot == null || dataSnapshot.getKey() == null)
                                    return;

                                long unreadMessagesCount;

                                if( dataSnapshot.getValue() == null)
                                    unreadMessagesCount = 0;
                                else
                                    unreadMessagesCount = (long) dataSnapshot.getValue();

                                if ( mDialogDialogsListAdapter != null){

                                    mDialogDialogsListAdapter.addItem(new Dialog(
                                            chatId,
                                            baseUser.firstName + " " + baseUser.lastName,
                                            baseUser.photoUrl,
                                            users,
                                            lastMessage,
                                            (int) unreadMessagesCount
                                    ));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot == null)
                    return;

                final String chatId = dataSnapshot.getKey();

                if (!chatId.contains(mACtiveUserUid)) // Ignoring unrelated events.
                    return;

                // Creating a new Dialog from the event:

                // extracting lastMessage:

                DBMessage dbMessage = dataSnapshot.getValue(DBMessage.class);
                final ChatMessage lastMessage = MessagesConverter
                        .fromDbMessageRefTOChatMessage(dbMessage);

                final String otherUid = ChatIdGenerator.extractOtherId(chatId, mACtiveUserUid);

                mUsersRef.child(otherUid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot == null)
                            return;

                        final BaseUser baseUser = dataSnapshot.getValue(BaseUser.class);



                        final ArrayList<ChatUser> users = new ArrayList<>();
                        users.add(lastMessage.getUser());


                        // Getting count of unseenMessages:

                        final DatabaseReference r =
                                mUnseenMessagesRef.child(chatId).child(mACtiveUserUid);

                        r.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot == null || dataSnapshot.getKey() == null)
                                    return;

                                long unreadMessagesCount;

                                if( dataSnapshot.getValue() == null)
                                    unreadMessagesCount = 0;
                                else
                                    unreadMessagesCount = (long) dataSnapshot.getValue(); // Updating UserContext by the new Dialog


                                if( mDialogDialogsListAdapter != null){
                                    mDialogDialogsListAdapter.updateItemById(new Dialog(
                                            chatId,
                                            baseUser.firstName + " " + baseUser.lastName,
                                            baseUser.photoUrl,
                                            users,
                                            lastMessage,
                                            (int) unreadMessagesCount));

//                                    mDialogDialogsListAdapter.updateDialogWithMessage(chatId, lastMessage);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
