package com.tennismate.tennismate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.tennismate.tennismate.bridge.FromBaseUserToDb;
import com.tennismate.tennismate.bridge.FromDBtoBaseUser;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.utilities.ICallback;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileFragment extends Fragment {

    // Global Data:

    private String mActiveUserId;
    private BaseUser mBaseUser;

    // View Elements:

    private CircleImageView mProfilePictImageView;
    private TextView mFullName;
    private TextView mEmailTextView;
    private Spinner mLevel;
    private int mLevelCounter;
    private ImageView mLevelInfo;
    private Button mSaveButton;

    // DTypes:
    private FromDBtoBaseUser mFromDBtoBaseUser;
    private FromBaseUserToDb mFromBaseUserToDb;

    public ProfileFragment() {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if( firebaseUser == null)
            return v;

        mActiveUserId = firebaseUser.getUid();
        initializeView(v);


        mFromDBtoBaseUser = new FromDBtoBaseUser(mActiveUserId, new ViewBinder(
                mFullName,
                mEmailTextView
        ));

        mFromBaseUserToDb = new FromBaseUserToDb();
        mFromDBtoBaseUser.execute();

        return v;
    }


    public void initializeView(View v){

        mProfilePictImageView = (CircleImageView) v.findViewById(R.id.profilePict);
        mFullName = (TextView) v.findViewById(R.id.fullName);
        mEmailTextView = (TextView) v.findViewById(R.id.email_field);
        mSaveButton = (Button) v.findViewById(R.id.save_button);
        mLevel = (Spinner) v.findViewById(R.id.level_field);
        mLevelCounter = 0;
        mLevel.post(new Runnable() {
            @Override
            public void run() {
                mLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mLevelCounter++;

                        if ( mLevelCounter > 1)
                            mSaveButton.setVisibility(View.VISIBLE);
                        mBaseUser.level = String.valueOf(position + 1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        mLevelInfo = (ImageView) v.findViewById(R.id.level_information);
        mLevelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), LevelInfoActivity.class);
                startActivity(in);
            }
        });

        mSaveButton.setVisibility(View.INVISIBLE);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFromBaseUserToDb.execute(mBaseUser);
                mSaveButton.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });


    }


    class  ViewBinder implements ICallback {

        private TextView mFullName;
        private TextView mEmailTextView;


        public ViewBinder(TextView mFullName, TextView mEmailTextView) {
            this.mFullName = mFullName;
            this.mEmailTextView = mEmailTextView;
        }

        @Override
        public void execute(Object o) {
            BaseUser baseUser = (BaseUser) o;
            ProfileFragment.this.mBaseUser = baseUser;
            mFullName.setText(baseUser.firstName + " " + baseUser.lastName);
            mEmailTextView.setText(baseUser.email);
            mLevel.setSelection(Integer.parseInt(baseUser.level) - 1);
            Picasso.with(getApplicationContext()).load(baseUser.photoUrl)
                    .into(mProfilePictImageView);

        }
    }
}

