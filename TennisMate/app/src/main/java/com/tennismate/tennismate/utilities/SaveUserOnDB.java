package com.tennismate.tennismate.utilities;


import android.os.AsyncTask;
import android.util.Log;

import com.facebook.AccessToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.user.User;


public class SaveUserOnDB extends AsyncTask<User, Void , Boolean> {
    private FirebaseDatabase database;

    @Override
    protected Boolean doInBackground(User... users) {

//        if( AccessToken.getCurrentAccessToken()!=null){
//            Log.d("FB", "FB");
//        }

        final User user = users[0];
        database = FirebaseDatabase.getInstance();
        final DatabaseReference usersRef = database.getReference();
        final DatabaseReference Users = usersRef.child("users");
        DatabaseReference uidQuery =  Users.child(user.uid);

        uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() == null ){

                    Users.setValue(user.uid);
                    Users.child(user.uid).setValue(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.d("User", firebaseError.getMessage());
            }
        });

        return true;
    }


}


/*

if(AccessToken.getCurrentAccessToken()!=null) {

    System.out.println(AccessToken.getCurrentAccessToken().getToken());

    GraphRequest request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken(),
            new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    // Application code
                    try {
                        String email = object.getString("email");
                        String gender = object.getString("gender");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    Bundle parameters = new Bundle();
    parameters.putString("fields", "id,name,email,gender,birthday");
    request.setParameters(parameters);
    request.executeAsync();

}
else
{
    System.out.println("Access Token NULL");
}
 */
