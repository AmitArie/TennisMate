package com.tennismate.tennismate;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.FirebaseDatabase;
import com.tennismate.tennismate.MateMatcher.MatchingFilter;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.utilities.GetUsersByFilterFromDB;
import com.tennismate.tennismate.utilities.RecyclerAdapter;

import java.util.ArrayList;

public class FindMateActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "FindMateActivity";
    private Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager  layoutManager;

    ArrayList<UserContext> mSearchResultUsersKeys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mate);

        this.mSearchResultUsersKeys = new ArrayList<>();

        MatchingFilter matchingFilter = (MatchingFilter) getIntent().getSerializableExtra("MatchingFilter");


//        Log.e(TAG, "Size of result: " + mSearchResultUsersKeys.size() );

        mToolbar = (Toolbar) findViewById(R.id.find_mate_toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.find_mate_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Updating the view with the results:

        adapter = new RecyclerAdapter(mSearchResultUsersKeys);

        recyclerView.setAdapter(adapter);

        new GetUsersByFilterFromDB(mSearchResultUsersKeys,
                matchingFilter,
                adapter).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<UserContext> newUserContextList = new ArrayList<>();

        for ( UserContext uc: mSearchResultUsersKeys){
            String name = uc.getUser().firstName.toLowerCase();

            if( name.contains(newText) ){
                newUserContextList.add(uc);

            }
        }

        adapter.setFilter(newUserContextList);
        return true;
    }
}
