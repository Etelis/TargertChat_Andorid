package com.example.targertchat.data.remote;

import com.example.targertchat.data.utils.LoginResponse;
import com.example.targertchat.data.model.User;
import com.example.targertchat.data.utils.FirebaseToken;
import com.example.targertchat.data.utils.LoginRequest;
import com.example.targertchat.data.utils.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Handle user requests from the API.
 */
public interface IUsersApi {
    @POST("Users/login")
    Call<LoginResponse> login(@Body LoginRequest userLogin);

    @POST("Users/register")
    Call<String> register(@Body RegisterRequest userRegister);

    @GET("Users/token")
    Call<User> token(@Header("Authorization") String token);

    @POST("Users/registerDevice")
    Call<Void> registerDevice(@Header("Authorization") String token, @Body FirebaseToken firebaseToken);
}