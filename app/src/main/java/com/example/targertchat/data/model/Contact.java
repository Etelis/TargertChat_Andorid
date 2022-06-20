package com.example.targertchat.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Contact Entity class.
 */
@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int identity;
    @SerializedName("id")
    private String contactID;
    @SerializedName("name")
    private String contactName;
    @SerializedName("server")
    private String server;
    @SerializedName("last")
    private String lastMessage;
    @SerializedName("lastdate")
    private String lastSeen;
    private String profilePic;

    public Contact(String contactID, String contactName, String server, String lastMessage, String lastSeen) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.server = server;
        this.lastMessage = lastMessage;
        this.lastSeen = lastSeen;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public int getIdentity() {
        return identity;
    }

    public String getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getServer() {
        return server;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
    }
}
