package com.tennismate.tennismate.utilities;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.user.User;
import com.tennismate.tennismate.user.UserContext;

import java.util.HashMap;

public class GetUserFromDB {

    private UserContext mUserContext;


    public GetUserFromDB(FirebaseUser fireBaseUser){

        final String userId = fireBaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("users");
        DatabaseReference uidQuery =  databaseRef.child(userId);

        uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap ServerUser = (HashMap) dataSnapshot.getValue();
                if( ServerUser != null ){
                    String firstName = (String) ServerUser.get("firstName");
                    String email = (String) ServerUser.get("email");
                    String level = (String)  ServerUser.get("level");
                    String photoUrl = (String) ServerUser.get("photoUrl");
                    String lastName = (String) ServerUser.get("lastName");
                    String country = (String)  ServerUser.get("country");
                    String district = (String)  ServerUser.get("district");
                    String street = (String)  ServerUser.get("street");

                    Double latitude = ((Number) ServerUser.get("latitude")).doubleValue();
                    Double longitude = ((Number) ServerUser.get("longitude")).doubleValue();
                    String lastUpdatedDate = (String)  ServerUser.get("lastUpdatedDate");

                    User user = new User(
                            userId,
                            firstName,
                            lastName,
                            email,
                            level,
                            photoUrl,
                            latitude,
                            longitude,
                            country,
                            district,
                            street,
                            lastUpdatedDate );

                    RunTimeSharedData.userContext = new UserContext(user);

                }
                else
                    Log.d("User", "user not found");
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("User", firebaseError.getMessage());
                mUserContext = null;

            }
        });


    }

    public UserContext getUserContext(){
        return mUserContext;
    }


}
