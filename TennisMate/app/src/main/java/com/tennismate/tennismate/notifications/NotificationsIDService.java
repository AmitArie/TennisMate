package com.tennismate.tennismate.notifications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class NotificationsIDService  extends FirebaseInstanceIdService {

    private final static String TAG = "NotificationsIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseUser  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if( firebaseUser == null)
            return;

        String activeUserId = firebaseUser.getUid();
        DatabaseReference accessTokRef = FirebaseDatabase.getInstance().
                getReference("accessTok");

        accessTokRef.child(activeUserId).setValue(refreshedToken);

    }
}
