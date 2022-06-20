package com.example.targertchat.data.remote;

import com.example.targertchat.data.model.Message;
import com.example.targertchat.data.utils.ContentToPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Handle message requests from the API.
 */
public interface IMessageAPI {
    @GET("Contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    @POST("Contacts/{id}/messages")
    Call<Void> postMessage(@Path("id") String id, @Body ContentToPost content, @Header("Authorization") String token);

    @GET("Contacts/{id}/messages/{id2}")
    Call<Message> getMessageById(@Path("id") String id, @Path("id2") String id2, @Header("Authorization") String token);

    @DELETE("Contacts/{id}/messages/{id2}")
    Call<Void> deleteMessageById(@Path("id") String id, @Path("id2") String id2, @Header("Authorization") String token);

    @PUT("Contacts/{id}/messages/{id2}")
    Call<Void> updateMessageById(@Path("id") String id, @Path("id2") String id2, @Header("Authorization") String token);
}
