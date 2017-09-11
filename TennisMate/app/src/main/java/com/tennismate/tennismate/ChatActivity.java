package com.tennismate.tennismate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.tennismate.tennismate.RunTimeSharedData.RunTimeSharedData;
import com.tennismate.tennismate.bridge.FromChatToDB;
import com.tennismate.tennismate.bridge.FromDBtoChat;
import com.tennismate.tennismate.chat.AppUtils;
import com.tennismate.tennismate.chat.ChatMessage;
import com.tennismate.tennismate.chat.MessagesFixtures;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity
        implements
            MessagesListAdapter.SelectionListener,
            MessagesListAdapter.OnLoadMoreListener, MessageInput.InputListener,
            MessageInput.AttachmentsListener
{

    private static final String TAG = "ChatActivity";
    private static final int TOTAL_MESSAGES_COUNT = 100;


    protected final String senderId = "0";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<ChatMessage> messagesAdapter;

    private Menu menu;
    private int selectionCount;
    private Date lastLoadedDate;
    private String chatId; // loaded via Intent.
    private MessagesList messagesList;
    private FromChatToDB fromChatToDB;
    private FromDBtoChat fromDBtoChat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunTimeSharedData.isChatActivityAtive = true;

        chatIdSetup();


        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(ChatActivity.this).load(url).into(imageView);
            }
        };

        setContentView(R.layout.activity_chat);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        this.fromChatToDB = new FromChatToDB(chatId, RunTimeSharedData.getUserContext());
        this.fromDBtoChat = new FromDBtoChat(chatId, getApplicationContext(), messagesAdapter);


        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);

        fromDBtoChat.loadAllMessages(chatId);
        fromDBtoChat.realTimeMessageListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RunTimeSharedData.isChatActivityAtive = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RunTimeSharedData.isChatActivityAtive = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RunTimeSharedData.isChatActivityAtive = true;
        Log.d(TAG, "Chat Activity Restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        RunTimeSharedData.isChatActivityAtive = false;
        Log.d(TAG, "Chat Activity Stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();
        RunTimeSharedData.isChatActivityAtive = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                messagesAdapter.deleteSelectedMessages();
                break;
            case R.id.action_copy:
                messagesAdapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
                AppUtils.showToast(this, R.string.copied_message, true);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            messagesAdapter.unselectAllItems();
        }
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            //loadMessages();
        }
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
        menu.findItem(R.id.action_copy).setVisible(count > 0);
    }


    // This function should connect to firebase.
    protected void loadMessages() {
//        new Handler().postDelayed(new Runnable() { //imitation of internet connection
//            @Override
//            public void run() {
//                ArrayList<ChatMessage> chatMessages = MessagesFixtures.getMessages(lastLoadedDate);
//                lastLoadedDate = chatMessages.get(chatMessages.size() - 1).getCreatedAt();
//                messagesAdapter.addToEnd(chatMessages, false);
//            }
//        }, 1000);
    }

    private MessagesListAdapter.Formatter<ChatMessage> getMessageStringFormatter() {
        return new MessagesListAdapter.Formatter<ChatMessage>() {
            @Override
            public String format(ChatMessage chatMessage) {
                String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                        .format(chatMessage.getCreatedAt());

                String text = chatMessage.getText();
                if (text == null) text = "[attachment]";

                return String.format(Locale.getDefault(), "%s: %s (%s)",
                        chatMessage.getUser().getName(), text, createdAt);
            }
        };
    }


    /**
     * adding new message to text.
     * @param input
     * @return
     */
    public boolean onSubmit(CharSequence input) {
        messagesAdapter.addToStart(fromChatToDB.writeMessage(input), true);
        return true;
    }

    public void onAddAttachments() {
        messagesAdapter.addToStart(
                MessagesFixtures.getImageMessage(), true);
    }

    private void initAdapter() {
        messagesAdapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setLoadMoreListener(this);
        messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<ChatMessage>() {
                    @Override
                    public void onMessageViewClick(View view, ChatMessage chatMessage) {
                        AppUtils.showToast(ChatActivity.this,
                                chatMessage.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(messagesAdapter);
    }

    /* Integration Methods */ //TODO: DELETE THIS COMMENT LATER.

    private void chatIdSetup(){
        Intent intent = getIntent();

        if( intent == null){
            Log.e(TAG, "Got null Intent");
            return;
        }


        Bundle b = intent.getExtras();

        if(b == null){
            Log.e(TAG, "Got good intent, but without any chadId");
            return;
        }
        this.chatId = b.getString("chatId");

    }
}
