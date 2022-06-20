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
import com.example.targertchat.data.utils.FirebaseToken;
import com.example.targertchat.data.utils.LoginRequest;
import com.example.targertchat.ui.contacts.ContactsActivity;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.to_register);
        Button loginButton = findViewById(R.id.login_button);


        userViewModel = new ViewModelProvider
                (this, new UserViewModelFactory()).get(UserViewModel.class);


        textView.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        // Listen to login button.
        loginButton.setOnClickListener((View v) -> {
            EditText username = findViewById(R.id.uesrname_text);
            EditText password = findViewById(R.id.password_text);

            // Check whether the user field is not empty.
            if (username.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "Username is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check whether the password field fits the rules.
            if (password.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "Password is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            // create new login request to the server.
            LoginRequest loginUser = new LoginRequest(username.getText().toString(), password.getText().toString());
            userViewModel.login(loginUser);

            // observe on the response from the server.
            userViewModel.isLoggedIn().observe(this, answerBoolean -> {
                if (answerBoolean) {
                    // if connection was successful, update the server with Firebase notification token
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, instanceIdResult -> {
                        String token = instanceIdResult.getToken();
                        FirebaseToken firebaseToken = new FirebaseToken(token);
                        userViewModel.notifyToken(firebaseToken);
                    });

                    // Move to contact activity.
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