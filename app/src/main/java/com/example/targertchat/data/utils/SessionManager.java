package com.example.targertchat.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.targertchat.R;
import com.example.targertchat.data.model.User;
import com.google.gson.Gson;

public class SessionManager {
    private SharedPreferences prefs;
    private final String USER_PREFS = "user_prefs";
    private static SessionManager instance;


    private SessionManager(Context context) {
        this.prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context){
        if (instance == null){
            instance = new SessionManager(context);
        }

        return instance;
    }

    public void saveSession(User user){
        Gson gson = new Gson();
        prefs.edit()
                .putString(USER_PREFS, gson.toJson(user))
                .apply();
    }

    public String fetchAuthToken(){
        Gson gson = new Gson();
        String json =  prefs.getString(USER_PREFS,null);

        if (json == null)
            return null;

        return gson.fromJson(json, User.class).getToken();
    }

    public User fetchSession (){
        Gson gson = new Gson();
        String json =  prefs.getString(USER_PREFS,null);

        if (json == null)
            return null;

        return gson.fromJson(json, User.class);
    }

    public void removeSession (){
        prefs.edit()
                .clear()
                .apply();
    }

}
