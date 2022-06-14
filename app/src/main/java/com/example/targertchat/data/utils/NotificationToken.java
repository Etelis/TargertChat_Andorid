package com.example.targertchat.data.utils;

import com.google.gson.annotations.SerializedName;

public class NotificationToken {
    @SerializedName("deviceId")
    public String token;

    public NotificationToken(String token) {
        this.token = token;
    }
}
