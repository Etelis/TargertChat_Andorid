package com.example.targertchat.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.targertchat.data.model.User;
import com.example.targertchat.data.repositories.UsersRepository;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;

public class UserViewModel extends ViewModel {

    private final UsersRepository usersRepository;
    private LiveData<User> currentUser;

    public UserViewModel(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public LiveData<User> login(PostLoginUser loginUser) {
        currentUser =  usersRepository.login(loginUser);
        return currentUser;
    }

    public LiveData<User> register(PostRegisterUser registerUser) {
        currentUser =  usersRepository.register(registerUser);
        return currentUser;
    }

    public LiveData<User> getCurrentUser(){
        return currentUser;
    }
}
