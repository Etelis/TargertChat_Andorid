package com.example.targertchat.ui.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.targertchat.R;

public class ContactDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_options);

        ImageView profileImage = findViewById(R.id.profile_image);
        Glide.with(this)
                .load(getIntent().getStringExtra("url"))
                .apply(new RequestOptions().placeholder(R.drawable.profile))
                .into(profileImage);

    }
}
