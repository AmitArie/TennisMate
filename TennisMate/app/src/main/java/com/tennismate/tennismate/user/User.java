package com.tennismate.tennismate.user;

import android.support.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

public class User
{

    public String uid;
    public String firstName;
    public String lastName;
    public String email;
    public int rank;
    public String imageUrl;
    public double latitude;
    public double longitude;
    public List<User> mates;

    @Keep
    public User(){};


    public User(String uid,
                String firstName,
                String lastName,
                String email,
                int rank,
                String imageUrl,
                double latitude,
                double longitude
    ) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rank = rank;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mates = new ArrayList<>();
    }

}
