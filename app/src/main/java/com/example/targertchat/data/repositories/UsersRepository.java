package com.example.targertchat.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.User;
import com.example.targertchat.data.remote.UsersApiManager;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;
import com.example.targertchat.data.utils.SessionManager;

public class UsersRepository {

    private static volatile UsersRepository instance;
    private final UsersApiManager usersApiManager;
    private final SessionManager sessionManager;


    private UsersRepository(UsersApiManager usersApiManager) {
        this.usersApiManager = usersApiManager;
        sessionManager = MainApplication.sessionManager;
    }

    public static UsersRepository getInstance(UsersApiManager usersApiManager) {
        if (instance == null) {
            instance = new UsersRepository(usersApiManager);
        }
        return instance;
    }

    public void login(PostLoginUser loginUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.login(loginUser, checkLoggedIn);
    }

    public void register(PostRegisterUser registerUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.register(registerUser, checkLoggedIn);
    }

    public boolean isSessionLoggedIn(){
        this.usersApiManager.verifyToken();
        User user = sessionManager.fetchSession();
        return user != null;
    }
}