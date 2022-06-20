package com.example.targertchat.data.utils;

import com.google.gson.annotations.SerializedName;

/**
 * ContactResponse - parameter passing for POST contact request.
 */
public class ContactResponse {
    @SerializedName("id")
    public String contactID;
    @SerializedName("name")
    public String contactName;
    @SerializedName("server")
    public String contactServer;

    public ContactResponse(String contactID, String contactName, String contactServer) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactServer = contactServer;
    }

    public String getContactID() {
        return contactID;
    }

}
