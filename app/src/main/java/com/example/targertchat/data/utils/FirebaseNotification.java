package com.example.targertchat.data.utils;

//Login User - Firebase Notification parameters.
public class FirebaseNotification {
    private String contactID;
    private String content;
    private String date;

    public FirebaseNotification(String contactID, String content, String date) {
        this.contactID = contactID;
        this.content = content;
        this.date = date;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
