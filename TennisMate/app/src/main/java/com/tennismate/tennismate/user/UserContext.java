package com.tennismate.tennismate.user;

import android.graphics.Bitmap;

import com.tennismate.tennismate.utilities.ImageDownloader;

import java.io.Serializable;
import java.util.List;


public class UserContext implements Serializable{

    private User mUser;
    private Bitmap mUserPhoto;
    public List<User> mMates;


    public UserContext(User user){
        this.mUser = user;
        this.mMates = null;

        new ImageDownloader(this)
                .execute(user.photoUrl);

    }

    public User getUser() {
        return mUser;
    }

    public Bitmap getUserPhoto() {
        return mUserPhoto;
    }

    public void setUserPhoto(Bitmap mUserPhoto) {
        this.mUserPhoto = mUserPhoto;
    }
}
