package com.example.targertchat.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.User;
import com.example.targertchat.data.remote.UsersApiManager;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;
import com.example.targertchat.data.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        this.usersApiManager.login(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User body = response.body();
                    sessionManager.saveSession(body);
                    checkLoggedIn.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                sessionManager.removeSession();
                checkLoggedIn.postValue(false);
            }
        }, loginUser);
    }

    public void register(PostRegisterUser registerUser, MutableLiveData<Boolean> checkLoggedIn) {
        this.usersApiManager.register(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    User body = registerUser.CreateUserWithToken(token);
                    sessionManager.saveSession(body);
                    checkLoggedIn.postValue(true);
                }
                else
                    checkLoggedIn.postValue(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sessionManager.removeSession();
                checkLoggedIn.postValue(false);
            }
        }, registerUser);
    }

    public boolean isSessionLoggedIn(){
        User user = sessionManager.fetchSession();
        return user != null;
    }
}