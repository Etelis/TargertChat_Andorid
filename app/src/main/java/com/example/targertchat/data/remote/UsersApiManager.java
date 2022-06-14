package com.example.targertchat.data.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.User;
import com.example.targertchat.data.utils.NotificationToken;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;
import com.example.targertchat.data.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersApiManager {
    private static IUsersApi service;
    private static UsersApiManager apiManager;
    private static SessionManager sessionManager;

    private UsersApiManager() {
        service = RetrofitService.createService(IUsersApi.class);
        sessionManager = MainApplication.sessionManager;
    }

    public static UsersApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new UsersApiManager();
        }
        return apiManager;
    }

    public void login(PostLoginUser loginUser, MutableLiveData<Boolean> checkLoggedIn) {
        Call<User> loginCall = service.login(loginUser);
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User body = response.body();
                    sessionManager.saveSession(body);
                    checkLoggedIn.postValue(true);
                }
                else {
                    sessionManager.removeSession();
                    checkLoggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                sessionManager.removeSession();
                checkLoggedIn.postValue(false);
            }
        });
    }

    public void notifyToken(NotificationToken notificationToken){
        Call<Void> notifyCall = service.registerDevice("Bearer " + sessionManager.fetchAuthToken(), notificationToken);
        notifyCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }

    public void register(PostRegisterUser registerUser, MutableLiveData<Boolean> checkLoggedIn){
        Call<String> registerCall = service.register(registerUser);
        registerCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    User newUser = registerUser.CreateUserWithToken(token);
                    sessionManager.saveSession(newUser);
                    checkLoggedIn.postValue(true);
                }
                else {
                    sessionManager.removeSession();
                    checkLoggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                sessionManager.removeSession();
                checkLoggedIn.postValue(false);
            }
        });
    }

    public void verifyToken(MutableLiveData<Boolean> checkSessionLoggedIn){
        Call<User> tokenCall = service.token("Bearer " + sessionManager.fetchAuthToken());
        tokenCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse( Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    String token = sessionManager.fetchAuthToken();
                    User body = response.body();

                    if (body != null) {
                        body.setToken(token);
                    }

                    sessionManager.saveSession(body);
                    checkSessionLoggedIn.postValue(true);
                }

                if(response.code()>= 400 && response.code() < 599){
                    sessionManager.removeSession();
                    checkSessionLoggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, Throwable t) {
                sessionManager.removeSession();
                checkSessionLoggedIn.postValue(false);
            }
        });
    }
}
