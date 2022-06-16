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
    private final LiveData<List<Contact>> contacts;
    private final MutableLiveData<Boolean> checkContactSubmitted;

    public ContactsViewModel(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
        checkContactSubmitted = new MutableLiveData<>();
        contacts = contactsRepository.getContacts();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }


    public void addContact(ContactResponse contactResponse) {
        contactsRepository.addContact(contactResponse, checkContactSubmitted);
    }

    public LiveData<Boolean> isContactSubmitted() {
        return checkContactSubmitted;
    }

    public void reload (){
        contactsRepository.reload();
    }
}
