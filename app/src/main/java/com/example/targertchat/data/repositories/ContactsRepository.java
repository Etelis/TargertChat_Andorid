package com.example.targertchat.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.Contact;
import com.example.targertchat.data.model.IContactDao;
import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.remote.IContactsApi;
import com.example.targertchat.data.remote.IInviteAPI;
import com.example.targertchat.data.remote.RetrofitService;
import com.example.targertchat.data.utils.ContactInvite;
import com.example.targertchat.data.utils.ContactResponse;
import com.example.targertchat.data.utils.NotificationMessageUpdate;
import com.example.targertchat.data.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsRepository {

    private static volatile ContactsRepository instance;
    private final IContactDao dao;
    private static SessionManager sessionManager;
    private static IContactsApi service;


    private ContactsRepository() {
        LocalDatabase db = LocalDatabase.getInstance();
        dao = db.contactDao();
        sessionManager = MainApplication.sessionManager;
        service = RetrofitService.createService(IContactsApi.class);
    }

    public static ContactsRepository getInstance() {
        if (instance == null) {
            instance = new ContactsRepository();
        }
        return instance;
    }

    public LiveData<List<Contact>> getContacts(){
        return dao.getAllContacts();
    }

    public void getContactsAPI() {
        Call<List<Contact>> getContactsCall = service.getContacts("Bearer " + sessionManager.fetchAuthToken());
        getContactsCall.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful()) {
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

    public void updateContactOnNewMessage(NotificationMessageUpdate update){
        new Thread(()-> {
            Contact contact = dao.getContactByID(update.getContactID());
            if (contact == null)
                return;

            contact.setLastMessage(update.getContent());
            contact.setLastSeen(update.getDate());
            dao.update(contact);
        }).start();
    }

    public void addContact(ContactResponse contactResponse, MutableLiveData<Boolean> checkContactSubmitted) {
        ContactInvite contactInvite = new ContactInvite(sessionManager.fetchSession().getUserName(), contactResponse.contactID, RetrofitService.DEFAULT_URL,contactResponse.contactServer);

        Call<Void> inviteContactCall = RetrofitService.createService(IInviteAPI.class, contactInvite.toServer).inviteContact(contactInvite, "Bearer " + sessionManager.fetchAuthToken());
        inviteContactCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Call<Void> addContactCall = service.addContact(contactResponse, "Bearer " + sessionManager.fetchAuthToken());
                    addContactCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful())
                                checkContactSubmitted.postValue(true);
                            else
                                checkContactSubmitted.postValue(false);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            checkContactSubmitted.postValue(false);
                        }
                    });
                }
                else
                    checkContactSubmitted.postValue(false);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                checkContactSubmitted.postValue(false);
            }
        });
    }

    public void clear() {
        new Thread(() -> {
            dao.clear();
        }).start();
    }
}
