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
        checkContactSubmitted = new MutableLiveData<>();
    }

    public LiveData<List<Contact>> getContacts() {
        return contactsRepository.getContacts();
    }

    public void getContactsFromAPI() {
        contactsRepository.getContactsAPI();
    }

    public void addContact(ContactResponse contactResponse) {
            contactsRepository.addContact(contactResponse, checkContactSubmitted);
    }

    public LiveData<Boolean> isContactSubmitted() {
        return checkContactSubmitted;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        contactsRepository.clear();
    }
}
