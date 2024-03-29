package com.example.targertchat.data.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.targertchat.MainApplication;
import com.example.targertchat.data.model.IMessageDao;
import com.example.targertchat.data.model.LocalDatabase;
import com.example.targertchat.data.model.Message;
import com.example.targertchat.data.remote.IMessageAPI;
import com.example.targertchat.data.remote.ITransferAPI;
import com.example.targertchat.data.remote.RetrofitService;
import com.example.targertchat.data.utils.MessageRequest;
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
    private final IMessageDao dao;

    private MessagesRepository() {
        webService = RetrofitService.createService(IMessageAPI.class);
        transferAPI = RetrofitService.createService(ITransferAPI.class);
        sessionManager = MainApplication.sessionManager;
        dao = LocalDatabase.getInstance().messageDao();
    }

    /**
     * gets an instance of message repository
     * @return MessageRepository
     */
    public static MessagesRepository getInstance() {
        if (instance == null) {
            instance = new MessagesRepository();
        }
        return instance;
    }

    /**
     * gets the messages of the contact
     * @param contactID
     * @return LiveData<List<Messages>> list of messages
     */
    public LiveData<List<Message>> getMessages(String contactID) {
        return dao.getAllMessages(contactID);
    }

    /**
     * inserts the message to the local database
     * @param message the message to insert
     */
    public void pushMessageToDAO(Message message) {
        new Thread(() -> {
           dao.insert(message);
        }).start();
    }

    /**
     * retrieves all the messages with the contact and put in the db
     * @param contactID
     */
    public void apiCallAndPutInDB (String contactID) {
        Call<List<Message>> getMessages = webService.getMessages(contactID, "Bearer " + sessionManager.fetchAuthToken());
        getMessages.enqueue(new Callback<List<Message>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    if (messages != null) {
                        messages.forEach((message -> {
                            message.setContactID(contactID);
                        }));
                    }
                    new Thread(() -> {
                        dao.clear(contactID);
                        dao.insertAll(messages);
                    }).start();
                }
            }
            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    /**
     * post  the message to the user and transfer the message to the contact
     * @param contactID
     * @param content content of the message
     * @param messageSubmitted flag if the message was submitted
     */
    public void postMessage(String contactID, MessageRequest content, MutableLiveData<Boolean> messageSubmitted) {

        TransferMessage transferMessage = new TransferMessage(
                MainApplication.sessionManager.fetchSession().getUserName(),
                contactID, content.getContent());
        Call<Void> callBackTransfer = transferAPI.transfer(transferMessage, "Bearer " + sessionManager.fetchAuthToken());
        callBackTransfer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
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
}
