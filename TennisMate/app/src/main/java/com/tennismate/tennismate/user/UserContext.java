package com.tennismate.tennismate.user;

import android.graphics.Bitmap;

import com.tennismate.tennismate.utilities.ImageDownloader;
import com.tennismate.tennismate.MateMatcher.RecyclerAdapter;

import java.io.Serializable;
import java.util.List;


public class UserContext implements Serializable{

    private BaseUser            mUser;
    private UserLocation        mUserLocation;
    private Bitmap              mUserPhoto;
    private List<BaseUser>      mMates;
    private RecyclerAdapter mAdapter;



    public UserContext(BaseUser user, UserLocation userLocation){
        this.mUser = user;
        this.mUserLocation = userLocation;
        this.mMates = null;

        new ImageDownloader(this)
                .execute(user.photoUrl);

    }

    public UserContext(BaseUser user, UserLocation userLocation,
                       RecyclerAdapter mAdapter){

        this.mUser = user;
        this.mUserLocation = userLocation;
        this.mMates = null;


        new ImageDownloader(this, mAdapter)
                .execute(user.photoUrl);

    }



    public UserContext(BaseUser user){
        this.mUser = user;
        this.mUserLocation = new UserLocation();
        this.mMates = null;


        new ImageDownloader(this)
                .execute(user.photoUrl);

    }

    public BaseUser getUser() {
        return mUser;
    }

    public UserLocation getUserLocation() {
        return mUserLocation;
    }


    public Bitmap getUserPhoto() {
        return mUserPhoto;
    }

    public void setUserPhoto(Bitmap mUserPhoto) {
        this.mUserPhoto = mUserPhoto;
    }

}
