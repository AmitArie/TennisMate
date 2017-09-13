package com.tennismate.tennismate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tennismate.tennismate.levelInfo.*;
import com.tennismate.tennismate.user.Level;
import com.tennismate.tennismate.user.LevelFactory;


import java.util.ArrayList;

public class LevelInfoActivity extends AppCompatActivity {

    private ArrayList<Level> mLevels;
    private RecyclerView mLevelsRv;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager  layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_info);

        mLevels = LevelFactory.getLevels();

        mLevelsRv = (RecyclerView) findViewById(R.id.levels_list);
        layoutManager = new LinearLayoutManager(this);

        mLevelsRv.setLayoutManager(layoutManager);
        mLevelsRv.setHasFixedSize(true);
        adapter = new RecyclerAdapter(mLevels);
        mLevelsRv.setAdapter(adapter);

    }
}
