package com.tennismate.tennismate.bridge;


import com.tennismate.tennismate.user.BaseUser;

public class FromBaseUserToDb extends BaseDB
{
    public void execute(BaseUser baseUser){
        mUsersRef.child(baseUser.uid).setValue(baseUser);
    }
}
