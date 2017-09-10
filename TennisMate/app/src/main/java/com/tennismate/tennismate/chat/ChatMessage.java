package com.tennismate.tennismate.chat;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;



public class ChatMessage implements IMessage,
        MessageContentType.Image, /*this is for default image messages implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {

    private String id;
    private String text;
    private Date createdAt;
    private ChatUser chatUser;
    private Image image;

    public ChatMessage(String id, ChatUser chatUser, String text) {
        this(id, chatUser, text, new Date());
    }

    public ChatMessage(String id, ChatUser chatUser, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.chatUser = chatUser;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public ChatUser getUser() {
        return this.chatUser;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }


    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    /* For future coding:


    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }

    */
}
