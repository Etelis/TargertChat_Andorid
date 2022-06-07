package com.example.targertchat.ui.contacts;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.repositories.ContactsRepository;

public class ContactsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContactsViewModel.class)) {
            return (T) new ContactsViewModel
                    (ContactsRepository.getInstance(MainApplication.contactsApiManager));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}