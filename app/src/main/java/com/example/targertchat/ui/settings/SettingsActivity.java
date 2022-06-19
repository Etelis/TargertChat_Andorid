package com.example.targertchat.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.targertchat.MainApplication;
import com.example.targertchat.R;
import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.remote.RetrofitService;
import com.example.targertchat.data.utils.SessionManager;
import com.example.targertchat.ui.MainActivity;
import com.example.targertchat.ui.contacts.ContactFragment;
import com.example.targertchat.ui.contacts.ContactsActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button exitBtn = findViewById(R.id.exit_settings_btn);
        Button updateServerBtn = findViewById(R.id.update_server_btn);
        EditText serverTxt = findViewById(R.id.server_update);


        updateServerBtn.setOnClickListener(v -> {
            if (serverTxt.getText().toString().equals("")) {
                Toast.makeText(SettingsActivity.this, "Server field cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (serverTxt.getText().toString().contains("http")){
                Toast.makeText(SettingsActivity.this, "Server should not contain http/s prefix!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!serverTxt.getText().toString().contains(":")){
                Toast.makeText(SettingsActivity.this, "Server should provide a port!", Toast.LENGTH_SHORT).show();
                return;
            }

            RetrofitService.DEFAULT_URL = serverTxt.getText().toString();
            Toast.makeText(SettingsActivity.this, "Successfuly changed default server address", Toast.LENGTH_SHORT).show();
        });
        exitBtn.setOnClickListener(v -> {
            finish();
        });

        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener((View v) -> {
            SessionManager.getInstance(this).removeSession();
            Intent i = new Intent(this, MainActivity.class);
            MainApplication.sessionManager.removeSession();
            new Thread(() -> {
                LocalDatabase.getInstance().clearAllTables();
            });
            startActivity(i);
            finishAffinity();
        });
    }
}