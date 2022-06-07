package com.example.targertchat.data.utils;

import com.example.targertchat.data.model.User;
import com.google.gson.annotations.SerializedName;

public class PostRegisterUser {
    @SerializedName("Username")
    private String UserName;
    @SerializedName("Password")
    private String Password;
    @SerializedName("DisplayName")
    private String DisplayName;
    @SerializedName("Photo")
    private String Photo;
    private String token;

    public PostRegisterUser(String userName, String password, String displayName, String photo) {
        UserName = userName;
        Password = password;
        DisplayName = displayName;
        Photo = photo;
    }

    public PostRegisterUser(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public User CreateUserWithToken(String token){
        return new User(this.UserName, this.DisplayName, this.Photo, token);
    }
}
