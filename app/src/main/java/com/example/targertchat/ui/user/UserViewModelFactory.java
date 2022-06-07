package com.example.targertchat.ui.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.repositories.UsersRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel
                    (UsersRepository.getInstance(MainApplication.usersApiManager));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}