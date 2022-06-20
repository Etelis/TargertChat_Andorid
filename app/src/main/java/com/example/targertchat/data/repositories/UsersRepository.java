package com.example.targertchat.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.data.remote.UsersApiManager;
import com.example.targertchat.data.utils.FirebaseToken;
import com.example.targertchat.data.utils.LoginRequest;
import com.example.targertchat.data.utils.RegisterRequest;

public class UsersRepository {

    private static volatile UsersRepository instance;
    private final UsersApiManager usersApiManager;


    private UsersRepository(UsersApiManager usersApiManager) {
        this.usersApiManager = usersApiManager;
    }

    /**
     * gets instance of the user repository
     * @return UserRepository
     */
    public static UsersRepository getInstance(UsersApiManager usersApiManager) {
        if (instance == null) {
            instance = new UsersRepository(usersApiManager);
        }
        return instance;
    }

    /**
     * login the user
     * @param loginUser the user to login
     * @param checkLoggedIn flag if the user logged in
     */
    public void login(LoginRequest loginUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.login(loginUser, checkLoggedIn);
    }

    /**
     * notifies the firebase token to the server
     * @param notificationToken
     */
    public void notifyToken(FirebaseToken notificationToken){
        this.usersApiManager.notifyFirebaseToServer(notificationToken);
    }

    /**
     * registers the user
     * @param registerUser the user to register
     * @param checkLoggedIn flag if the user has registered successfully
     */
    public void register(RegisterRequest registerUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.register(registerUser, checkLoggedIn);
    }

    /**
     * check if the session is valid
     * @param checkSessionLoggedIn flag to see if the session is valid
     */
    public void isSessionLoggedIn(MutableLiveData<Boolean> checkSessionLoggedIn){
        this.usersApiManager.checkSessionConnected(checkSessionLoggedIn);
    }
}