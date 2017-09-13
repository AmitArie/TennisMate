package com.tennismate.tennismate.bridge;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.utilities.ICallback;

public class FromDBtoBaseUser extends BaseDB  {

    private ICallback mCallback;
    private String mUserId;


    public FromDBtoBaseUser(String userId, final ICallback callback) {

        this.mUserId = userId;
        this.mCallback = callback;
    }

    public void execute(){

        mUsersRef.child(mUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot == null || dataSnapshot.getKey() == null)
                    return;

                BaseUser user = dataSnapshot.getValue(BaseUser.class);
                mCallback.execute(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
