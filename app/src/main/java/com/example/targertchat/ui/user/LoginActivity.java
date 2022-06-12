package com.example.targertchat.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.R;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.ui.contacts.ContactsActivity;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider
                (this, new UserViewModelFactory()).get(UserViewModel.class);

        TextView tv = findViewById(R.id.to_register);
        tv.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener((View v) -> {
            EditText username = findViewById(R.id.uesrname_text);
            EditText password = findViewById(R.id.password_text);
            if (username.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "Username is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "Password is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            PostLoginUser loginUser = new PostLoginUser(username.getText().toString(), password.getText().toString());
            userViewModel.login(loginUser);
            userViewModel.isLoggedIn().observe(this, answerBoolean -> {
                if (answerBoolean) {
                    Intent intent = new Intent(this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Either user does not exist or server is not responsive.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}