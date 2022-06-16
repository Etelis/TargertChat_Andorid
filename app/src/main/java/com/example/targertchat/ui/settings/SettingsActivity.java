package com.example.targertchat.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.targertchat.R;
import com.example.targertchat.data.utils.SessionManager;
import com.example.targertchat.ui.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button exitBtn = findViewById(R.id.exit_settings_btn);
        exitBtn.setOnClickListener((View v) -> {
            finish();
        });

        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener((View v) -> {
            SessionManager.getInstance(this).removeSession();
            Intent i = new Intent(this, MainActivity.class);
            finishAffinity();
            startActivity(i);
        });
    }
}