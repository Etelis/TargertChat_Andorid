package com.example.targertchat.data.utils;

public class NotificationMessageUpdate {
    private String contactID;
    private String content;
    private String date;

    public NotificationMessageUpdate(String contactID, String content, String date) {
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
