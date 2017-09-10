package com.tennismate.tennismate.chat;


import java.io.Serializable;

public class DBMessage implements Serializable{

    public String uid;          // the uid of author of the message
    public String name;         // the name of author of the message
    public String urlPict;      // the picture url  of author of the message
    public String message;      // the message of the author of the message
    public long timestamp;


    public DBMessage(){};


    public DBMessage(String uid, String name, String urlPict, String message, long timestamp) {
        this.uid = uid;
        this.name = name;
        this.urlPict = urlPict;
        this.message = message;
        this.timestamp = timestamp;
    }
}

