package com.example.targertchat.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.data.model.Contact;
import com.example.targertchat.data.model.IContactDao;
import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.remote.ContactsApiManager;
import com.example.targertchat.data.utils.ContactResponse;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {

    private static volatile ContactsRepository instance;
    private final ContactsApiManager contactsApiManager;
    private final ContactListData contactListData;
    private final IContactDao dao;


    private ContactsRepository(ContactsApiManager contactsApiManager) {
        this.contactsApiManager = contactsApiManager;
        LocalDatabase db = LocalDatabase.getInstance();
        dao = db.contactDao();
        contactListData = new ContactListData();
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            reload();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                contactListData.postValue(dao.getAllContacts());
            }).start();
        }
    }

    public static ContactsRepository getInstance(ContactsApiManager contactsApiManager) {
        if (instance == null) {
            instance = new ContactsRepository(contactsApiManager);
        }
        return instance;
    }

    public LiveData<List<Contact>> getContacts(){
        return contactListData;
    }

//    public LiveData<Contact> getContactByID(String contactID) {
//        return this.contactsApiManager.getContactByID(String contactID);
//    }

    public void addContact(ContactResponse postContact, MutableLiveData<Boolean> checkContactSubmitted){
        this.contactsApiManager.addContact(postContact, checkContactSubmitted);
    }

    public void reload(){
        contactsApiManager.getContacts(dao);
        new Thread(() -> {
            contactListData.postValue(dao.getAllContacts());
        }).start();
    }
}
