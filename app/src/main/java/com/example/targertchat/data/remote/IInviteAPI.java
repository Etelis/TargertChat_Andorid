package com.example.targertchat.data.remote;

import com.example.targertchat.data.utils.ContactInvite;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IInviteAPI {
    @POST("invite")
    Call<Void> inviteContact(@Body ContactInvite contactInvite, @Header("Authorization") String token);
}
