package com.example.targertchat.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.targertchat.R;
import com.example.targertchat.data.utils.ContactResponse;
import com.example.targertchat.ui.adapters.FragmentAdapter;
import com.example.targertchat.ui.settings.SettingsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ContactsActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private FloatingActionButton addContactBtn;
    private AlertDialog dialog;
    private TabLayout tabLayout;
    private ContactsViewModel contactsViewModel;
    private FloatingActionButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        addContactBtn = findViewById(R.id.addContact);
        settingsBtn = findViewById(R.id.settings_btn);


        settingsBtn.setOnClickListener((View v) -> {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        });

        contactsViewModel = new ViewModelProvider
                (this, new ContactsViewModelFactory()).get(ContactsViewModel.class);

        viewPager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        viewPager2.setAdapter(new FragmentAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
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
                });
        tabLayoutMediator.attach();

        AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this, R.style.dialog_theme);
        View view = getLayoutInflater().inflate(R.layout.add_contact_dialog, null);
        EditText contactUName = view.findViewById(R.id.contact_username);
        EditText contactName = view.findViewById(R.id.contact_name);
        EditText contactServer = view.findViewById(R.id.contact_server);
        Button submitContact = view.findViewById(R.id.submit_contact);
        submitContact.setOnClickListener(v -> {
            String userName = contactUName.getText().toString();
            String name = contactName.getText().toString();
            String server = contactServer.getText().toString();

            // checking if the entered text is empty or not.
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(server)) {
                Toast.makeText(ContactsActivity.this, "Fields cannot stay empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            ContactResponse contactResponse = new ContactResponse(userName, name, server);
            contactsViewModel.addContact(contactResponse);
            contactsViewModel.isContactSubmitted().observe(this, answerBoolean -> {
                if (answerBoolean) {
                    contactUName.setText("");
                    contactName.setText("");
                    contactServer.setText("");
                    dialog.dismiss();
                    contactsViewModel.getContactsFromAPI();
                } else {
                    Toast.makeText(ContactsActivity.this, "Either contact exists or server is not responsive.", Toast.LENGTH_SHORT).show();
                }
            });
        });
        builder.setView(view);
        dialog = builder.create();
        addContactBtn.setOnClickListener(v -> dialog.show());
    }
}