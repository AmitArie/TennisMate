package com.tennismate.tennismate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.user.User;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.utilities.RoundedImageView;
import com.tennismate.tennismate.utilities.Time;


public class ProfileActivity extends AppCompatActivity {
    private UserContext mUserContext;
    private User mUser;
    private RoundedImageView mProfilePictImageView;
    private TextView mFullName;
    private TextView mEmailTextView;
    private Spinner  mLevel;
    private Button   mSaveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUserContext = RunTimeSharedData.userContext;
        mUser = mUserContext.getUser();

    }


    @Override
    protected void onStart() {
        super.onStart();

        mProfilePictImageView = (RoundedImageView) findViewById(R.id.profilePict);
        mProfilePictImageView.setImageBitmap(mUserContext.getUserPhoto());

        // Full Name

        mFullName = (TextView) findViewById(R.id.fullName);
        mFullName.setText(mUser.firstName + " " + mUser.lastName);

        // Email
        mEmailTextView = (TextView) findViewById(R.id.email_field);
        mEmailTextView.setText(mUser.email);

        // Level

        //TODO: add information about the level, in another activity, use level factory

        mLevel = (Spinner) findViewById(R.id.level_field);
        mLevel.setSelection(Integer.parseInt(mUser.level) - 1);

        mLevel.post(new Runnable() {
            @Override
            public void run() {
                mLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mSaveButton.setVisibility(View.VISIBLE);
                        mUser.level = String.valueOf(position + 1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


        // Save button:

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setVisibility(View.INVISIBLE);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference usersRef = database.getReference();
                final DatabaseReference Users = usersRef.child("users");

                mUser.lastUpdatedDate = Time.getFullTime();
                Users.child(mUser.uid).setValue(mUser);
                mSaveButton.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
