package com.example.targertchat.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.remote.UsersApiManager;
import com.example.targertchat.data.utils.NotificationToken;
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

    /**
     * gets instance of the user repository
     * @param usersApiManager
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
    public void login(PostLoginUser loginUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.login(loginUser, checkLoggedIn);
    }

    /**
     * notifies the firebase token to the server
     * @param notificationToken
     */
    public void notifyToken(NotificationToken notificationToken){
        this.usersApiManager.notifyFirebaseToServer(notificationToken);
    }

    /**
     * registers the user
     * @param registerUser the user to register
     * @param checkLoggedIn flag if the user has registered successfully
     */
    public void register(PostRegisterUser registerUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.register(registerUser, checkLoggedIn);
    }

    /**
     * check if the session is valid
     * @param checkSessionLoggedIn flag to see if the session is valid
     */
    public void isSessionLoggedIn(MutableLiveData<Boolean> checkSessionLoggedIn){
        this.usersApiManager.verifyToken(checkSessionLoggedIn);
    }
}