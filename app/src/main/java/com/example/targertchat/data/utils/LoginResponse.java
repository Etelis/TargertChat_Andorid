package com.example.targertchat.data.utils;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("UserId")
    public String UserId;
    @SerializedName("FirstName")
    public String FirstName;
    @SerializedName("LastName")
    public String LastName;
    @SerializedName("ProfilePicture")
    public String ProfilePicture;
    @SerializedName("Password")
    public String Password;
    @SerializedName("ResponseCode")
    public String ResponseCode;
    @SerializedName("ResponseMessage")
    public String ResponseMessage;

    public LoginResponse(String UserId, String Password) {
        this.UserId = UserId;
        this.Password = Password;
    }

    public String getUserId() {
        return UserId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }
}