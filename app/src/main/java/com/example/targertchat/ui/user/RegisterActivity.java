package com.example.targertchat.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.R;
import com.example.targertchat.data.repositories.UsersRepository;
import com.example.targertchat.data.utils.PostRegisterUser;
import com.example.targertchat.ui.contacts.ContactsActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNameEdt, passwordEdt, verifyPasswordEdt, displayNameEdt;
    private Button registerBtn;
    private UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameEdt = findViewById(R.id.uesrname_text);
        passwordEdt = findViewById(R.id.password_text);
        verifyPasswordEdt = findViewById(R.id.verifyPassword_text);
        displayNameEdt = findViewById(R.id.displayName_text);
        registerBtn = findViewById(R.id.registerBtn);

        UserViewModel userViewModel = new ViewModelProvider
                (this, new UserViewModelFactory()).get(UserViewModel.class);


        registerBtn.setOnClickListener((View v)-> {
                // on below line we are getting data from our edit text.
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String verifyPassword = verifyPasswordEdt.getText().toString();
                String displayName = displayNameEdt.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(password, verifyPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(displayName)) {
                    Toast.makeText(RegisterActivity.this, "Display Name is required!", Toast.LENGTH_SHORT).show();
                }

                PostRegisterUser registerUser = new PostRegisterUser(userName, password, displayName, null);
                userViewModel.register(registerUser);
                userViewModel.isLoggedIn().observe(this, answerBoolean -> {
                if (answerBoolean) {
                    Intent intent = new Intent(this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Either user exists or server is not responsive.", Toast.LENGTH_SHORT).show();
                }
            });
            });
        }
    }