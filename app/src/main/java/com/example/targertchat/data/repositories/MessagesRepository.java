package com.example.targertchat.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.IMessageDao;
import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.model.Message;
import com.example.targertchat.data.remote.IMessageAPI;
import com.example.targertchat.data.remote.ITransferAPI;
import com.example.targertchat.data.remote.RetrofitService;
import com.example.targertchat.data.utils.ContentToPost;
import com.example.targertchat.data.utils.SessionManager;
import com.example.targertchat.data.utils.TransferMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesRepository {

    private static IMessageAPI webService;
    private static ITransferAPI transferAPI;
    private static SessionManager sessionManager;
    private static volatile MessagesRepository instance;
    private IMessageDao dao;

    private MessagesRepository() {
        webService = RetrofitService.createService(IMessageAPI.class);
        transferAPI = RetrofitService.createService(ITransferAPI.class);
        sessionManager = MainApplication.sessionManager;
        dao = LocalDatabase.getInstance().messageDao();
    }

    public static MessagesRepository getInstance() {
        if (instance == null) {
            instance = new MessagesRepository();
        }
        return instance;
    }

    public LiveData<List<Message>> getMessages() {
        return dao.getAllMessages();
    }

    public void apiCallAndPutInDB (String contactID) {
        Call<List<Message>> getMessages = webService.getMessages(contactID, "Bearer " + sessionManager.fetchAuthToken());
        getMessages.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    new Thread(() -> {
                        dao.clear();
                        dao.insertAll(messages);
                    }).start();
                }
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                dao.clear();
            }
        });
    }

    public void postMessage(String contactID, ContentToPost content, MutableLiveData<Boolean> messageSubmitted) {
        Call<Void> callback = webService.postMessage(contactID, content, "Bearer " + sessionManager.fetchAuthToken());
        callback.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    messageSubmitted.postValue(true);
                    apiCallAndPutInDB(contactID);
                } else {
                    messageSubmitted.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                messageSubmitted.postValue(false);
            }
        });
    }

    public void transfer(TransferMessage transferMessage) {
        transferAPI.transfer(transferMessage);
    }
}
