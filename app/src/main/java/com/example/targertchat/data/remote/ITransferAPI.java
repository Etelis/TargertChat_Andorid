package com.example.targertchat.data.remote;


import com.example.targertchat.data.utils.TransferMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Handle transfer request of a message to a given contact from the API.
 */
public interface ITransferAPI {
    @POST("transfer")
    Call<Void> transfer(@Body TransferMessage transferMessage, @Header("Authorization") String token);
}
