package com.example.targertchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.R;
import com.example.targertchat.data.utils.FirebaseToken;
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

        // Check if session stil valid.
        userViewModel.checkSession();

        // handle animation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(this::checkIfUserIsAuthenticated, SPLASH_TIMER);

    }

    /**
     * Check if user's session is still valid
     */
    private void checkIfUserIsAuthenticated() {

        // Listen to the response from the server.
        userViewModel.isSessionLoggedIn().observe(this, aBoolean -> {
            Intent mainIntent;

            // On succesful response from the server send Firebase token to server.
            if (aBoolean) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashScreen.this, instanceIdResult -> {
                    String token = instanceIdResult.getToken();
                    FirebaseToken firebaseToken = new FirebaseToken(token);
                    userViewModel.notifyToken(firebaseToken);
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