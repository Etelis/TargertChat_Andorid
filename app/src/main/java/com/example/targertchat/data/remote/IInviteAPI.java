package com.example.targertchat.data.remote;

import com.example.targertchat.data.utils.InviteContact;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Handle invitation request from the API.
 */
public interface IInviteAPI {
    @POST("api/invite")
    Call<Void> inviteContact(@Body InviteContact contactInvite, @Header("Authorization") String token);
}
