package com.example.targertchat.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.targertchat.R;
import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.model.Message;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String contactID = getIntent().getStringExtra("id");

    }
}