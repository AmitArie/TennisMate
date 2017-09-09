package com.tennismate.tennismate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.tennismate.tennismate.MateMatcher.MatchingFilter;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.utilities.GetUsersByFilterFromDB;
import com.tennismate.tennismate.utilities.RecyclerAdapter;

import java.util.ArrayList;

public class FindMateActivity extends AppCompatActivity {

    private static final String TAG = "FindMateActivity";
    private Toolbar mToolbar;
    private UserContext mUserContext;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager  layoutManager;

    ArrayList<UserContext> arrayList;
    ArrayList<String> mSearchResultUsersKeys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mate);

        this.mSearchResultUsersKeys = new ArrayList<>();

        MatchingFilter matchingFilter = (MatchingFilter) getIntent().getSerializableExtra("MatchingFilter");
        GetUsersByFilterFromDB getUsersByFilterFromDB = new GetUsersByFilterFromDB(mSearchResultUsersKeys, matchingFilter);
        getUsersByFilterFromDB.execute();

        Log.e(TAG, "Size of result: " + mSearchResultUsersKeys.size() );

//        mUserContext = RunTimeSharedData.userContext;
//        mToolbar = (Toolbar) findViewById(R.id.find_mate_toolbar);
//        setSupportActionBar(mToolbar);
//        arrayList = new ArrayList<>();
//
//        recyclerView = (RecyclerView) findViewById(R.id.find_mate_recyclerview);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//
//        arrayList.add(mUserContext);
//        adapter = new RecyclerAdapter(arrayList);
//        recyclerView.setAdapter(adapter);

    }



}
