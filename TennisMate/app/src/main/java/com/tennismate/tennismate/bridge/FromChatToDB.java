package com.tennismate.tennismate.bridge;

import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.ChatUser;
import com.tennismate.tennismate.chat.DBMessage;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;

import java.util.Date;

public final class FromChatToDB extends BaseChatDB
{

    private static final String STROKE_COLOR_BLUE =  "0";

    private UserContext userContext;
    private BaseUser baseUser;
    private ChatUser chatUser;

    public FromChatToDB(String chatId, UserContext userContext) {
        super(chatId);

        this.userContext = userContext;
        this.baseUser = this.userContext.getUser();
        this.chatUser = new ChatUser(
                STROKE_COLOR_BLUE,
                this.baseUser.firstName,
                this.baseUser.photoUrl,
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

        // appending the chat messages:

        DBMessage DBMessage = MessagesConverter.fromChatToDbMessageRef(chatMessage);
        this.mMessagesRef.child(chatId).child(chatMessage.getId()).setValue(DBMessage);

        // Saving in last message :

        this.mLastMessages.child(chatId).setValue(DBMessage);
        return chatMessage;
    }





}
