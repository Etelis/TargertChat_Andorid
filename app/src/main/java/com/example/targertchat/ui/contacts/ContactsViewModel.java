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
    private LiveData<List<Contact>> contacts;
    private MutableLiveData<Boolean> checkContactSubmited;

    public ContactsViewModel(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
        checkContactSubmited = new MutableLiveData<Boolean>();
        contacts = contactsRepository.getContacts();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

//    public LiveData<Contact> getContactByID(String id) {
//        return contactsRepository.getContactByID(id);
//    }

    public void addContact(ContactResponse contactResponse) {
        contactsRepository.addContact(contactResponse, checkContactSubmited);
    }

    public LiveData<Boolean> isContactSubmited() {
        return checkContactSubmited;
    }

    public void reload (){
        contactsRepository.reload();
    }
}
