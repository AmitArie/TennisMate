package com.tennismate.tennismate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.tennismate.tennismate.bridge.FromDBtoDialogs;
import com.tennismate.tennismate.mates.Dialog;



public class MyMatesFragment extends Fragment implements DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    private ImageLoader imageLoader;
    private DialogsListAdapter<Dialog> dialogsAdapter;
    private DialogsList dialogsList;
    private FromDBtoDialogs fromDBtoDialogs;


    public MyMatesFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_mates, container, false);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(getActivity()).load(url).into(imageView);
            }
        };
        dialogsList = (DialogsList) view.findViewById(R.id.dialogsList);
        initAdapter();
        fromDBtoDialogs = new FromDBtoDialogs(dialogsAdapter);



        return view;
    }


    private void initAdapter() {

        dialogsAdapter = new DialogsListAdapter<>(imageLoader);
        // maybe here we need to set the list of dialogs to the adaptors.
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
