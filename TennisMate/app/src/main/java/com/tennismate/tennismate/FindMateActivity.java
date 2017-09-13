package com.tennismate.tennismate;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.tennismate.tennismate.MateMatcher.CustomItemClickListener;
import com.tennismate.tennismate.MateMatcher.MatchingFilter;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.utilities.ChatIdGenerator;
import com.tennismate.tennismate.utilities.GetUsersByFilterFromDB;
import com.tennismate.tennismate.MateMatcher.RecyclerAdapter;

import java.util.ArrayList;

public class FindMateActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "FindMateActivity";

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager  layoutManager;
    private ArrayList<UserContext> mSearchResultUsersKeys;
    private String mActiveUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mate);

        this.mActiveUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.mSearchResultUsersKeys = new ArrayList<>();

        MatchingFilter matchingFilter = (MatchingFilter) getIntent().getSerializableExtra("MatchingFilter");



        mToolbar = (Toolbar) findViewById(R.id.find_mate_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("People around you .. ");

        recyclerView = (RecyclerView) findViewById(R.id.find_mate_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Updating the view with the results:

        adapter = new RecyclerAdapter(mSearchResultUsersKeys, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                UserContext userContext  = mSearchResultUsersKeys.get(position);
                BaseUser user = userContext.getUser();

                String OtherUid = user.uid;

                String chatId = ChatIdGenerator.generate(mActiveUserId, OtherUid);

                Intent intent = new Intent(FindMateActivity.this, ChatActivity.class);
                intent.putExtra("chatId",chatId);
                startActivity(intent);
            }
        });

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
