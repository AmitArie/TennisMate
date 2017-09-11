package com.tennismate.tennismate.mates;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.List;


public interface IDialog<MESSAGE extends IMessage> {

    String getId();

    String getDialogPhoto();

    String getDialogName();

    List<? extends IUser> getUsers();

    MESSAGE getLastMessage();

    void setLastMessage(MESSAGE message);

    int getUnreadCount();
}
