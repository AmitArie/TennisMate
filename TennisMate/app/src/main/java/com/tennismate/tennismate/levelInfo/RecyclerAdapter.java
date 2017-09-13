package com.tennismate.tennismate.levelInfo;


import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tennismate.tennismate.R;
import com.tennismate.tennismate.user.Level;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private ArrayList<Level> mLevels;

    public RecyclerAdapter (ArrayList<Level> levels){
        this.mLevels = levels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.level_info_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Level l = mLevels.get(position);

        holder.mLevelHeader.setText(l.mName);
        holder.mLevelBody.setText(l.mDescription);
    }


    public int getItemCount() {
        return mLevels.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mLevelHeader;
        TextView mLevelBody;

        public MyViewHolder(View v){
            super(v);

            mLevelHeader =(TextView) v.findViewById(R.id.level_info_row_header);
            mLevelHeader.setTypeface(null, Typeface.BOLD);
            mLevelHeader.setTextColor(Color.BLACK);

            mLevelBody =(TextView) v.findViewById(R.id.level_info_row_body);
            mLevelBody.setTextColor(Color.GRAY);
        }

    }
}
