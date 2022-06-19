package com.example.targertchat.data.utils;

import com.example.targertchat.data.model.User;
import com.google.gson.annotations.SerializedName;

public class LoginUser {
    @SerializedName("user")
    private User user;
    @SerializedName("token")
    private String token;

    public LoginUser(User user, String token) {
        this.user = new User(user.getUserName(), user.getDisplayName(), user.getPhoto(), token);
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
