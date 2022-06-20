package com.example.targertchat.data.utils;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

/**
 * InviteContact - Parameter Passing class used at utils for API and parameter passing request.
 */
public class InviteContact {
    @SerializedName("from")
    public String fromUser;
    @SerializedName("to")
    public String toContact;
    @SerializedName("server")
    public String fromServer;
    @Ignore
    public String toServer;

    public InviteContact(String fromUser, String toContact, String fromServer, String toServer) {
        this.fromUser = fromUser;
        this.toContact = toContact;
        this.fromServer = fromServer;
        this.toServer = toServer;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToContact() {
        return toContact;
    }

    public String getFromServer() {
        return fromServer;
    }

    public String getToServer() {
        return toServer;
    }
}
