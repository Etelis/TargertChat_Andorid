package com.example.targertchat.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.targertchat.data.repositories.UsersRepository;
import com.example.targertchat.data.utils.NotificationToken;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;

public class UserViewModel extends ViewModel {

    private final UsersRepository usersRepository;
    private final MutableLiveData<Boolean> checkSessionLoggedIn;
    private final MutableLiveData<Boolean> checkLoggedIn;

    public UserViewModel(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        checkLoggedIn = new MutableLiveData<>();
        checkSessionLoggedIn = new MutableLiveData<>();
    }

    public void notifyToken(NotificationToken notificationToken){
        usersRepository.notifyToken(notificationToken);
    }

    public void login(PostLoginUser loginUser) {
        usersRepository.login(loginUser, checkLoggedIn);
    }

    public void register(PostRegisterUser registerUser) {
        usersRepository.register(registerUser, checkLoggedIn);
    }

    public LiveData<Boolean> isLoggedIn() {
        return checkLoggedIn;
    }

    public LiveData<Boolean> isSessionLoggedIn() {
        return checkSessionLoggedIn;
    }

    public void checkSession(){
        usersRepository.isSessionLoggedIn(checkSessionLoggedIn);
    }
}
