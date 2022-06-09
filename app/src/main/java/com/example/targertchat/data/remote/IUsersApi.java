package com.example.targertchat.data.remote;

import com.example.targertchat.data.model.User;
import com.example.targertchat.data.utils.PostLoginUser;
import com.example.targertchat.data.utils.PostRegisterUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IUsersApi {
    @POST("Users/login")
    Call<User> login(@Body PostLoginUser userLogin);

    @POST("Users/register")
    Call<String> register(@Body PostRegisterUser userRegister);

    @GET("Users/token")
    Call<User> token(@Header("Authorization") String token);
}