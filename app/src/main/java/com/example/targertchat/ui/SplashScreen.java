package com.example.targertchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.R;
import com.example.targertchat.data.utils.NotificationToken;
import com.example.targertchat.ui.contacts.ContactsActivity;
import com.example.targertchat.ui.user.UserViewModel;
import com.example.targertchat.ui.user.UserViewModelFactory;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 3000;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        userViewModel = new ViewModelProvider
                (this, new UserViewModelFactory()).get(UserViewModel.class);

        userViewModel.checkSession();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(this::checkIfUserIsAuthenticated, SPLASH_TIMER);

    }

    private void checkIfUserIsAuthenticated() {
        userViewModel.isSessionLoggedIn().observe(this, aBoolean -> {
            Intent mainIntent;
            if (aBoolean) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashScreen.this, instanceIdResult -> {
                    String token = instanceIdResult.getToken();
                    NotificationToken notificationToken = new NotificationToken(token);
                    userViewModel.notifyToken(notificationToken);
                });
                mainIntent = new Intent(SplashScreen.this, ContactsActivity.class);
            } else {
                mainIntent = new Intent(SplashScreen.this, MainActivity.class);
            }
            startActivity(mainIntent);
            finish();
        });
    }
}