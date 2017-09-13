package com.tennismate.tennismate;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tennismate.tennismate.MateMatcher.MatchingFilter;
import com.tennismate.tennismate.user.UserLocation;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  {


    private ImageView           mSearchMatesButton;
    private SeekBar             mRadiusSeekBar;
    private TextView            mRadiusSeekBarText;
    private String              mActiveUserUid;
    private DatabaseReference   mUserLocationRef;

    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        this.mActiveUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.mUserLocationRef = FirebaseDatabase.getInstance().getReference("location_meta");
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        searchMatesSetup(v);
        //profileSetup(v);
        //logoutSetup(v);
        //chatButtonSetup(v);

        return v;
    }


    private void searchMatesSetup(View v){

        mSearchMatesButton = (ImageView) v.findViewById(R.id.search_mates_button);
        mSearchMatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Find TennisMates According to:");

                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.search_mates_filter_dialog, (ViewGroup) getView(), false);

                setupRadiusSeek(viewInflated); // stting up elements inside the dialog.



                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchMates();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }


    private void searchMates(){


        mUserLocationRef.child(mActiveUserUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if( dataSnapshot == null || dataSnapshot.getKey() == null)
                            return;

                        UserLocation userLocation = dataSnapshot.getValue(UserLocation.class);
                        int radius = mRadiusSeekBar.getProgress();


                        MatchingFilter matchingFilter = createFilter(radius, userLocation);
                        Intent intent = new Intent(getActivity(), FindMateActivity.class);
                        intent.putExtra("MatchingFilter", matchingFilter);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    private MatchingFilter createFilter (int radius, UserLocation userLocation){
        return new MatchingFilter(radius, userLocation.latitude, userLocation.longitude);
    }

    private void setupRadiusSeek(View v) {

        mRadiusSeekBar = (SeekBar) v.findViewById(R.id.radiusSeekBar);
        mRadiusSeekBarText = (TextView) v.findViewById(R.id.radius_seek_bar_text);


        mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();

                mRadiusSeekBarText.setVisibility(View.VISIBLE);
                mRadiusSeekBarText.setText("  " + progress + " km");
                mRadiusSeekBarText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRadiusSeekBarText.setVisibility(View.INVISIBLE);
                    }
                }, 1500);
            }

        });
    }

//    private void profileSetup(View v){
//
//        profileButton = (FloatingActionButton) v.findViewById(R.id.profileButton);
//        profileButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void logoutSetup(View v){
//
//        mLogout = (Button) v.findViewById(R.id.logout);
//        mLogout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                LoginManager.getInstance().logOut();
//                mAuth =  FirebaseAuth.getInstance();
//                mAuth.signOut();
//                Intent intent = new Intent(getActivity(), EntryActivity.class);
//                startActivity(intent);
//
//            }
//        });
//    }
//
//    private void chatButtonSetup(View v){
//        mChatButton = (Button) v.findViewById(R.id.chat_activity_button);
//        mChatButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ChatActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}


