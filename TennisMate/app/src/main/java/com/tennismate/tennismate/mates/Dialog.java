package com.tennismate.tennismate.mates;

import com.tennismate.tennismate.chat.ChatMessage;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.tennismate.tennismate.chat.ChatUser;

import java.util.ArrayList;


public class Dialog implements IDialog<ChatMessage> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<ChatUser> users;
    private ChatMessage lastMessage;

    private int unreadCount;

    public Dialog(String id, String name, String photo,
                  ArrayList<ChatUser> users, ChatMessage lastMessage, int unreadCount) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<ChatUser> getUsers() {
        return users;
    }

    @Override
    public ChatMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(ChatMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        Dialog d = (Dialog) obj;

        if( this.id.equals(d.getId()))
            return true;
        return false;
    }
}
