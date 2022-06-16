package com.example.targertchat;
import android.app.Application;

import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.remote.ContactsApiManager;
import com.example.targertchat.data.utils.SessionManager;
import com.example.targertchat.data.remote.UsersApiManager;

public class MainApplication extends Application {

    public static UsersApiManager usersApiManager;
    public static ContactsApiManager contactsApiManager;
    public static SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = SessionManager.getInstance(this);
        usersApiManager = UsersApiManager.getInstance();
        contactsApiManager = ContactsApiManager.getInstance();
        LocalDatabase.initializeDB(this);
    }
}