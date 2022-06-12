package com.example.targertchat.data.remote;

import com.example.targertchat.data.model.Contact;
import com.example.targertchat.data.utils.ContactResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IContactsApi {
    @GET("Contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("Contacts")
    Call<Void> addContact(@Body ContactResponse contactResponse, @Header("Authorization") String token);

    @GET("Contacts/{id}")
    Call<Contact> getContactById(@Path("id") String id, @Header("Authorization") String token);
}
