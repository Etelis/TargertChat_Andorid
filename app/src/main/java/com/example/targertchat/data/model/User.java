package com.example.targertchat.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    private String userName;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("photo")
    private String photo;
    private String token;

    public User(String userName, String displayName, String photo, String token) {
        this.userName = userName;
        this.displayName = displayName;
        this.photo = photo;
        this.token = token;
    }

    public User() {
    }

    public String getToken() { return token; }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
