package com.tennismate.tennismate.bridge;

import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.ChatUser;
import com.tennismate.tennismate.chat.DBMessage;

import java.util.Date;


public class MessagesConverter {

    private static final String STROKE_COLOR_BLUE =  "0";
    private static final String STROKE_COLOR_WHITE = "1";
    private static final String mActiveUserUid = RunTimeSharedData
            .getUserContext().getUser().uid;

    public static DBMessage fromChatToDbMessageRef(ChatMessage chatMessage){

        return new DBMessage(
                mActiveUserUid,
                chatMessage.getUser().getName(),
                chatMessage.getUser().getAvatar(),
                chatMessage.getText(),
                chatMessage.getCreatedAt().getTime()
        );
    }

    public static ChatMessage fromDbMessageRefTOChatMessage(DBMessage DBMessage){


        String color = STROKE_COLOR_WHITE;

        if( mActiveUserUid.equals(DBMessage.uid) )
            color = STROKE_COLOR_BLUE;

        ChatUser chatUser = new ChatUser(
                color,
                DBMessage.name,
                DBMessage.urlPict,true);

        return new ChatMessage(
                    Long.toString(DBMessage.timestamp),
                    chatUser,
                    DBMessage.message,
                    new Date(DBMessage.timestamp
                ));
    }
}
