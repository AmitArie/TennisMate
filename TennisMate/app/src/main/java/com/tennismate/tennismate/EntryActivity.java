package com.tennismate.tennismate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tennismate.tennismate.bridge.FromUserContextToDB;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.utilities.SaveUserOnDB;

import java.util.Arrays;



public class EntryActivity extends AppCompatActivity {

    private SignInButton mGoogleButton;
    private static final int RC_GOOGLE_SIGN_IN = 1;

    private GoogleApiClient mGoogleApiClient; // google
    private CallbackManager mCallbackManager; // facebook

    private static final String TAG = "ENTRY_ACTIVITY";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry2);

        mAuth =  FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    startActivity( new Intent(EntryActivity.this, SplashActivity.class));
                }
            }
        };

        googleSignInSetup();
        facebookSignInSetup();
    }


    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        //checkLocationPerm();
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_GOOGLE_SIGN_IN) {

            // GOOGLE
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
        else{
            // FACEBOOK:
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    /*  Facebook SignIn : start */

    private void facebookSignInSetup(){

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            String facebookUserId = mAuth.getCurrentUser().getProviderData().get(1).getUid();
                            String PhotoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
                            saveUserOnDB(FirebaseAuth.getInstance().getCurrentUser(), PhotoUrl);

                        } else {
                            LoginManager.getInstance().logOut();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(EntryActivity.this, "Email address is associated with different provider. ",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /*  Facebook SignIn : end */


    /*  Google SignIn : start */

    private void googleSignInSetup(){

        mGoogleButton = (SignInButton) findViewById(R.id.googleBtn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(EntryActivity.this, "You got an error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
            }
        });
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( ! task.isSuccessful() ) {
                            Log.d(TAG, "signInWithCredential:failed");
                        }
                        else{
                            // updating DB:
                            String photoUrl = mAuth.getCurrentUser().getProviderData().get(1).getPhotoUrl().toString();
                            saveUserOnDB(FirebaseAuth.getInstance().getCurrentUser(), photoUrl);
                        }
                    }
                });
    }

    /*  Google SignIn : end */


    private void saveUserOnDB (final FirebaseUser firebaseUser, final String photoUrl){


        BaseUser baseUser = new BaseUser(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                "10",
                photoUrl);

        new FromUserContextToDB(new UserContext(baseUser))
                .execute();
        //SaveUserOnDB.firstTime(new UserContext(baseUser));

    }

}
