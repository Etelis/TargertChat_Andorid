package com.example.targertchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener((View v)-> checkDataEntered());



    }

    protected boolean checkDataEntered() {
        EditText username = (EditText) findViewById(R.id.uesrname_text);
        EditText password = (EditText) findViewById(R.id.password_text);
        if(password.getText().toString().equals("") ) {
            TextView passwordWarning = (TextView) findViewById(R.id.password_warning);
            passwordWarning.setVisibility(View.VISIBLE);
        }
        if(username.getText().toString().equals("") ) {
            TextView usernameWarning = (TextView) findViewById(R.id.username_warning);
            usernameWarning.setVisibility(View.VISIBLE);
        }

        // Check data base


        return false;
    }

}