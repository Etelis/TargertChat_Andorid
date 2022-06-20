package com.example.targertchat.data.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.User;
import com.example.targertchat.data.utils.FirebaseToken;
import com.example.targertchat.data.utils.LoginRequest;
import com.example.targertchat.data.utils.LoginResponse;
import com.example.targertchat.data.utils.RegisterRequest;
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

    /**
     * Singleton design pattern.
     * @return - singleton UsersApiManager object.
     */
    public static UsersApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new UsersApiManager();
        }
        return apiManager;
    }

    /**
     * login - handle login request.
     * @param loginUser - login user object format matchs API format.
     * @param checkLoggedIn - Mutuable boolean to notify log in was successful.
     */
    public void login(LoginRequest loginUser, MutableLiveData<Boolean> checkLoggedIn) {
        Call<LoginResponse> loginCall = service.login(loginUser);
        loginCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Recive user, created new user Object and append token.
                    LoginResponse body = response.body();
                    body.getUser().setToken(body.getToken());

                    // update session manager with the new user,
                    sessionManager.saveSession(body.getUser());

                    // update boolean mutable data.
                    checkLoggedIn.postValue(true);

                } else {
                    // O.W notify operation failed.
                    onFailure(call, new Exception(String.valueOf(response.code())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                checkLoggedIn.postValue(false);
            }
        });
    }

    /**
     * notifyFirebaseToServer - Send a POST request to the server notifying it with the Firebase provided token.
     * @param firebaseToken - Firebase token.
     */
    public void notifyFirebaseToServer(FirebaseToken firebaseToken){
        Call<Void> notifyCall = service.registerDevice("Bearer " + sessionManager.fetchAuthToken(), firebaseToken);
        notifyCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }

    /**
     * register - handle register request to server.
     * @param registerUser - login user object format matchs API format.
     * @param checkLoggedIn - Mutuable boolean to notify log in was successful.
     */
    public void register(RegisterRequest registerUser, MutableLiveData<Boolean> checkLoggedIn){
        Call<String> registerCall = service.register(registerUser);
        registerCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    // if request was successful, update session manager with the provided user.
                    String token = response.body();
                    User newUser = registerUser.CreateUserWithToken(token);
                    sessionManager.saveSession(newUser);
                    checkLoggedIn.postValue(true);
                } else {
                    // O.W notify operation failed.
                    onFailure(call, new Exception(String.valueOf(response.code())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                checkLoggedIn.postValue(false);
            }
        });
    }

    /**
     * checkSessionConnected - check if the user has a valid session with the server.
     * @param checkSessionLoggedIn - notify app if session still available.
     */
    public void checkSessionConnected(MutableLiveData<Boolean> checkSessionLoggedIn){
        Call<User> tokenCall = service.token("Bearer " + sessionManager.fetchAuthToken());
        tokenCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse( Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // retrieve JWT token from Session Manager
                    String token = sessionManager.fetchAuthToken();
                    User body = response.body();

                    // Make sure a token was provided by the server.
                    if (body != null) {
                        body.setToken(token);
                    }

                    sessionManager.saveSession(body);
                    checkSessionLoggedIn.postValue(true);
                }

                if(response.code()>= 400 && response.code() < 599){
                    // O.W notify operation failed.
                    onFailure(call, new Exception(String.valueOf(response.code())));
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
