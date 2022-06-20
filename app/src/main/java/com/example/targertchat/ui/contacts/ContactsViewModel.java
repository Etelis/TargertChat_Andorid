package com.example.targertchat.ui.contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.targertchat.data.model.Contact;
import com.example.targertchat.data.repositories.ContactsRepository;
import com.example.targertchat.data.utils.ContactResponse;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private final ContactsRepository contactsRepository;
    private final MutableLiveData<Boolean> checkContactSubmitted;

    public ContactsViewModel(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;

        // MutableLiveData to notify activity server has added the contact.
        checkContactSubmitted = new MutableLiveData<>();
    }

    // Get all contacts from the local DB
    public LiveData<List<Contact>> getContacts() {
        return contactsRepository.getContacts();
    }

    // Fetch all contacts from the API
    public void getContactsFromAPI() {
        contactsRepository.getContactsAPI();
    }

    // Add new contact to server, invite contact to char as well.
    public void addContact(ContactResponse contactResponse) {
            contactsRepository.addContact(contactResponse, checkContactSubmitted);
    }

    // return live data notifying status of request.
    public LiveData<Boolean> isContactSubmitted() {
        return checkContactSubmitted;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        contactsRepository.clear();
    }
}
