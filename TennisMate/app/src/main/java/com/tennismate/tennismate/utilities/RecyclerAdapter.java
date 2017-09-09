package com.tennismate.tennismate.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tennismate.tennismate.R;
import com.tennismate.tennismate.user.BaseUser;
import com.tennismate.tennismate.user.UserContext;
import com.tennismate.tennismate.user.UserLocation;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<UserContext> mUserContextArrayList = new ArrayList<>();

    public RecyclerAdapter (ArrayList<UserContext> userContextArrayList){
        this.mUserContextArrayList = userContextArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matcher_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserContext userContext  = mUserContextArrayList.get(position);
        BaseUser user = userContext.getUser();
        UserLocation userLocation = userContext.getUserLocation();
        String userName = user.firstName + " " + user.lastName;
        String userLocationS =  userLocation.district + " " + userLocation.street;

        holder.mUserPict.setImageBitmap(userContext.getUserPhoto());
        holder.mUserName.setText(userName);
        holder.muserLocation.setText(userLocationS);
    }

    @Override
    public int getItemCount() {
        return mUserContextArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mUserPict;
        TextView  mUserName;
        TextView  muserLocation;

        public MyViewHolder(View v){
            super(v);

            mUserPict = (ImageView) v.findViewById(R.id.matcher_row_pict);
            mUserName = (TextView) v.findViewById(R.id.matcher_row_full_name);
            muserLocation = (TextView) v.findViewById(R.id.matcher_row_location);

        }
    }
}
