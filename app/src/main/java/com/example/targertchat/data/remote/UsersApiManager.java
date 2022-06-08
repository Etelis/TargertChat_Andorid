package com.example.targertchat.data.remote;

import com.example.targertchat.data.model.User;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;

import retrofit2.Call;
import retrofit2.Callback;

public class UsersApiManager {
    private static IUsersApi service;
    private static UsersApiManager apiManager;

    private UsersApiManager() {
        service = RetrofitService.createService(IUsersApi.class);
    }

    public static UsersApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new UsersApiManager();
        }
        return apiManager;
    }

    public void login(Callback<User> callback, PostLoginUser loginUser){
        Call<User> loginCall = service.login(loginUser);
        loginCall.enqueue(callback);
    }

    public void register(Callback<String> callback, PostRegisterUser registerUser){
        Call<String> registerCall = service.register(registerUser);
        registerCall.enqueue(callback);
    }
}
