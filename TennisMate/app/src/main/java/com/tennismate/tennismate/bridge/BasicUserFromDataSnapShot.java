package com.tennismate.tennismate.bridge;

import com.google.firebase.database.DataSnapshot;
import com.tennismate.tennismate.user.BaseUser;

import java.util.HashMap;



public class BasicUserFromDataSnapShot {

    public static BaseUser create(final Object dataSnapshotValue){

        HashMap ServerUser = (HashMap) dataSnapshotValue;

        if ( ServerUser == null)
            return null;

        String uid = (String) ServerUser.get("uid");
        String firstName = (String) ServerUser.get("firstName");
        String lastName = (String) ServerUser.get("lastName");
        String email = (String) ServerUser.get("email");
        String level = (String)  ServerUser.get("level");
        String photoUrl = (String) ServerUser.get("photoUrl");

        return new BaseUser(
                uid,
                firstName,
                lastName,
                email,
                level,
                photoUrl);
    }
}
