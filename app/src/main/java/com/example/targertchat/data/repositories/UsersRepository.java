package com.example.targertchat.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.remote.UsersApiManager;
import com.example.targertchat.data.utils.FirebaseToken;
import com.example.targertchat.data.utils.LoginRequest;
import com.example.targertchat.data.utils.RegisterRequest;
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

    public void login(LoginRequest loginUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.login(loginUser, checkLoggedIn);
    }

    public void notifyToken(FirebaseToken firebaseToken){
        this.usersApiManager.notifyFirebaseToServer(firebaseToken);
    }

    public void register(RegisterRequest registerUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.register(registerUser, checkLoggedIn);
    }

    public void isSessionLoggedIn(MutableLiveData<Boolean> checkSessionLoggedIn){
        this.usersApiManager.checkSessionConnected(checkSessionLoggedIn);
    }
}