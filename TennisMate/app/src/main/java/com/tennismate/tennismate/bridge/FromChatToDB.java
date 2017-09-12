package com.tennismate.tennismate.bridge;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.ChatUser;
import com.tennismate.tennismate.chat.DBMessage;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.utilities.ChatIdGenerator;

import java.util.Date;

public final class FromChatToDB extends BaseDB
{

    private static final String STROKE_COLOR_BLUE =  "0";

    private String mChatId;
    private BaseUser mBaseUser;
    private ChatUser chatUser;

    public FromChatToDB(String chatId, BaseUser baseUser) {
        super();

        this.mChatId = chatId;
        this.mBaseUser = baseUser;
        this.chatUser = new ChatUser(
                STROKE_COLOR_BLUE,
                this.mBaseUser.firstName,
                this.mBaseUser.photoUrl,
                true);
    }


    public ChatMessage writeMessage(CharSequence msg){

        Date d = new Date();
        ChatMessage chatMessage = new ChatMessage(
                Long.toString(d.getTime()),
                this.chatUser,
                msg.toString(),
                d
        );

        DBMessage DBMessage = MessagesConverter.fromChatToDbMessageRef(chatMessage);

        this.mMessagesRef.child(mChatId).child(chatMessage.getId())
                .setValue(DBMessage); // appending the chat messages:

        this.mLastMessagesRef.child(mChatId).setValue(DBMessage); // Saving in last message
        appendUnseenMessages();

        return chatMessage;
    }


    public void appendUnseenMessages(){


        final String otherUserUid = ChatIdGenerator.extractOtherId(mChatId, mBaseUser.uid);
        final DatabaseReference r = this.mUnseenMessagesRef.child(mChatId).child(otherUserUid);
        r.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot == null || dataSnapshot.getValue() == null)
                    r.setValue(1);
                else{
                    long count = (long) dataSnapshot.getValue();
                    r.setValue(count + 1 );
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
