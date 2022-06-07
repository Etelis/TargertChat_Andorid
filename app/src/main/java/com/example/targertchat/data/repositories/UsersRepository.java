package com.example.targertchat.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.User;
import com.example.targertchat.data.remote.SessionManager;
import com.example.targertchat.data.remote.UsersApiManager;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersRepository {

    private static volatile UsersRepository instance;
    private final UsersApiManager usersApiManager;
    private final MutableLiveData<User> user = new MutableLiveData<>();
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

    public MutableLiveData<User> login(PostLoginUser loginUser){
        this.usersApiManager.login(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User body = response.body();
                    user.setValue(body);
                    sessionManager.saveAuthToken(user.getValue().getToken());
                } else{
                    user.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                user.postValue(null);
            }
        }, loginUser);

        return user;
    }

    public MutableLiveData<User> register(PostRegisterUser registerUser){
        this.usersApiManager.register(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String token = response.body();
                    User body = registerUser.CreateUserWithToken(token);
                    sessionManager.saveAuthToken(token);
                    user.setValue(body);
                } else{
                    user.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                user.postValue(null);
            }
        }, registerUser);

        return user;
    }

}