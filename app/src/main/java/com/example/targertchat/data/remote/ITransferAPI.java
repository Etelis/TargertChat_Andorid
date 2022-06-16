package com.example.targertchat.data.remote;


import com.example.targertchat.data.utils.TransferMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ITransferAPI {
    @POST("transfer")
    Call<Void> transfer(@Body TransferMessage transferMessage);
}
