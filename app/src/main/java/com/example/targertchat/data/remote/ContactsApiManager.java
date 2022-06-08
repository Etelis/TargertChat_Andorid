package com.example.targertchat.data.remote;

import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.Contact;
import com.example.targertchat.data.model.IContactDao;
import com.example.targertchat.data.utils.ContactResponse;
import com.example.targertchat.data.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsApiManager {
    private static IContactsApi service;
    private static ContactsApiManager apiManager;
    private static SessionManager sessionManager;

    private ContactsApiManager() {
        service = RetrofitService.createService(IContactsApi.class);
        sessionManager = MainApplication.sessionManager;
    }

    public static ContactsApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ContactsApiManager();
        }
        return apiManager;
    }

    public void getContacts(IContactDao dao){
        Call<List<Contact>> getContactsCall = service.getContacts("Bearer " + sessionManager.fetchAuthToken());
        getContactsCall.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful()){
                    List<Contact> contacts = (List<Contact>) response.body();
                    new Thread(() -> {
                        dao.clear();
                        dao.insertAll(contacts);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                dao.clear();
            }
        });
    }

    public void addContact(ContactResponse contactResponse, MutableLiveData<Boolean> checkContactSubmited){
        Call<Void> addContactCall = service.addContact(contactResponse, "Bearer " + sessionManager.fetchAuthToken());
        addContactCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    checkContactSubmited.postValue(true);
                else
                    checkContactSubmited.postValue(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                checkContactSubmited.postValue(false);
            }
        });
    }
}
