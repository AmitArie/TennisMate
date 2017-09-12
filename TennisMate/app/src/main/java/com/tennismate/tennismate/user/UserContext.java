package com.tennismate.tennismate.user;

import android.graphics.Bitmap;

import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.mates.Dialog;
import com.tennismate.tennismate.utilities.ImageDownloader;
import com.tennismate.tennismate.MateMatcher.RecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserContext{

    private BaseUser                    mUser;
    private UserLocation                mUserLocation;
    private Bitmap                      mUserPhoto;
    private List<Dialog>                mDialogs;
    private DialogsListAdapter<Dialog>  mDialogsAdapter;
    private RecyclerAdapter             mAdapter;


    public UserContext(){}

    public UserContext(BaseUser user, UserLocation userLocation){
        this.mUser = user;
        this.mUserLocation = userLocation;
        this.mDialogs = null;

        new ImageDownloader(this)
                .execute(user.photoUrl);

    }

    public UserContext(BaseUser user, UserLocation userLocation,
                       RecyclerAdapter mAdapter){

        this.mUser = user;
        this.mUserLocation = userLocation;
        this.mDialogs = null;


        new ImageDownloader(this, mAdapter)
                .execute(user.photoUrl);

    }



    public UserContext(BaseUser user){
        this.mUser = user;
        this.mUserLocation = new UserLocation();
        this.mDialogs = null;


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



    public void setUser(BaseUser user) {
        this.mUser = user;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.mUserLocation = userLocation;
    }


    public void setUserPhoto(Bitmap mUserPhoto) {
        this.mUserPhoto = mUserPhoto;
    }


    public List<Dialog> getDialog(){
        return mDialogs;
    }
    public void setDialogs(List<Dialog> dialogs) {
        this.mDialogs = dialogs;
    }




    public void updateDialog(Dialog replacement){

        /*
               It seems that
               we are removing the replacement,
               and inserting it all over again.
               BUT, in Dialog class, equality of Dialogs
               checks only if the ids are the same.
               so it is fine.
         */
        int oldPos = mDialogs.indexOf(replacement);
        mDialogs.remove(oldPos);
        mDialogs.add(replacement); // for persistence (on Memory)

        if( this.mDialogsAdapter != null){
            this.mDialogsAdapter.notifyDataSetChanged();
            this.mDialogsAdapter.sortByLastMessageDate();
        }
    }

    public void setDialogAdapter(DialogsListAdapter<Dialog>  dialogsAdapter){
        this.mDialogsAdapter = dialogsAdapter;
    }

    public void eraseUnreadMessageCount(String chatId){

        for ( Dialog d : mDialogs){
            if( d.getId().equals(chatId) ){

                d.setUnreadCount(0);

                if(this.mDialogsAdapter != null)
                    this.mDialogsAdapter.notifyDataSetChanged();

                break;
            }
        }
    }
}
