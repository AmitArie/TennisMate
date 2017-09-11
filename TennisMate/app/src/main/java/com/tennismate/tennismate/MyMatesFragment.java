package com.tennismate.tennismate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.mates.Dialog;
import com.tennismate.tennismate.mates.DialogsFixtures;
import com.tennismate.tennismate.user.UserContext;


public class MyMatesFragment extends Fragment implements DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsAdapter;
    private DialogsList dialogsList;
    private UserContext mUserContext;


    public MyMatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUserContext = RunTimeSharedData.getUserContext();
        View view = inflater.inflate(R.layout.fragment_my_mates, container, false);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(getActivity()).load(url).into(imageView);
            }
        };
        mUserContext = RunTimeSharedData.getUserContext();
        dialogsList = (DialogsList) view.findViewById(R.id.dialogsList);
        initAdapter();


        return view;
    }


    private void initAdapter() {

        dialogsAdapter = new DialogsListAdapter<>(imageLoader);
        mUserContext.setDialogAdapter(dialogsAdapter);
        dialogsAdapter.setItems(mUserContext.getDialog());
        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);
        dialogsList.setAdapter(dialogsAdapter);


    }

    @Override
    public void onDialogClick(Dialog dialog) {
        // Entering specific chat

        dialog.setUnreadCount(0);
        String chatId = dialog.getId();
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("chatId", chatId);
        startActivity(intent);
    }

    @Override
    public void onDialogLongClick(Dialog dialog) {

    }
}
