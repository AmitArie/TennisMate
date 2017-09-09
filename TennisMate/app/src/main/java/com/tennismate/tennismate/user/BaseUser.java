package com.tennismate.tennismate.user;

import android.support.annotation.Keep;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BaseUser implements  Serializable
{

    public String uid;
    public String firstName;
    public String lastName;
    public String email;
    public String level;
    public String photoUrl;


    @Keep
    public BaseUser(){};

    public BaseUser(String uid, String fullName, String email,  String level,
                String photoUrl) {

        this.uid = uid;
        this.firstName = "";
        this.lastName= "";

        if( fullName != null ) {
            String [] splitted = fullName.split("\\s+");
            this.firstName = splitted[0];
            this.lastName = splitted[1];
        }

        this.email = email;
        this.level = level;
        this.photoUrl = photoUrl;
    }


    public BaseUser(String uid, String firstName, String lastName, String email,
                String level, String photoUrl) {

        this.uid = uid;
        this.firstName = firstName;
        this.lastName= lastName;
        this.email = email;
        this.level = level;
        this.photoUrl = photoUrl;
    }



}
