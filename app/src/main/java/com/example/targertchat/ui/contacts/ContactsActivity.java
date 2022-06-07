package com.example.targertchat.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.targertchat.R;
import com.example.targertchat.ui.adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel viewModel;
    private ViewPager2 viewPager2;
    TabLayout tabLayout;
    ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsViewModel = new ViewModelProvider
                (this, new ContactsViewModelFactory()).get(ContactsViewModel.class);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiper);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            contactsViewModel.reload();
        });

        viewPager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        viewPager2.setAdapter(new FragmentAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Chats");
                        tab.setIcon(getResources().getDrawable(R.drawable.ic_action_message));
                        break;
                    }

                    case 1: {
                        tab.setText("Camera");
                        tab.setIcon(getResources().getDrawable(R.drawable.ic_action_camera));
                        break;

                    }
                }
            }
        });
        tabLayoutMediator.attach();

    }
}