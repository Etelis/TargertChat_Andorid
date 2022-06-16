package com.example.targertchat.ui.chat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.targertchat.MainApplication;
import com.example.targertchat.R;
import com.example.targertchat.data.model.Message;
import com.example.targertchat.data.repositories.MessagesRepository;
import com.example.targertchat.data.utils.ContentToPost;
import com.example.targertchat.data.utils.TransferMessage;
import com.example.targertchat.ui.MainActivity;
import com.example.targertchat.ui.contacts.ContactsActivity;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;
    private RecyclerView messagesRecycler;
    private MessagesListAdapter messagesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String contactID = getIntent().getStringExtra("id");
        viewModel = new ChatViewModel(MessagesRepository.getInstance());
        viewModel.getMessagesFromApi(contactID);
        messagesRecycler = findViewById(R.id.message_list);

        viewModel.getMessages().observe(this, messageListVal -> {
            Collections.sort(messageListVal, (s1, s2) -> s1.getId() - s2.getId());
            messagesListAdapter = new MessagesListAdapter(this, messageListVal);
            messagesRecycler.setLayoutManager(new LinearLayoutManager(this));
            messagesRecycler.setAdapter(messagesListAdapter);
        });


        final NestedScrollView scrollview = findViewById(R.id.scroll);
        scrollview.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(NestedScrollView.FOCUS_DOWN);
            }
        }, 100);

        ImageButton sendBtn = findViewById(R.id.send_button);
        EditText input = findViewById(R.id.input);
        sendBtn.setOnClickListener((View v) -> {
            if(input.getText().toString().equals("")) {
                return;
            }
            ContentToPost content = new ContentToPost(input.getText().toString());
            viewModel.postMessage(getIntent().getStringExtra("id"), content);
            viewModel.isMessageSubmitted().observe(this, answer -> {
                if(!answer) {
                    Toast.makeText(ChatActivity.this, "Error in sending the message", Toast.LENGTH_SHORT).show();
                } else {
                    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    scrollview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.fullScroll(NestedScrollView.FOCUS_DOWN);
                        }
                    }, 100);

                    TransferMessage transferMessage = new TransferMessage(
                            MainApplication.sessionManager.fetchSession().getUserName(),
                            contactID, input.getText().toString());

                    viewModel.transferMessage(transferMessage);

                    input.setText("");
                }
            });
;        });
    }
}