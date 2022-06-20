package com.example.targertchat.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.targertchat.data.model.Message;
import com.example.targertchat.data.repositories.MessagesRepository;
import com.example.targertchat.data.utils.MessageRequest;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private final MessagesRepository messagesRepository;
    private final MutableLiveData<Boolean> messageSubmitted;

    public ChatViewModel(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
        messageSubmitted = new MutableLiveData<>();
    }

    public void getMessagesFromApi(String contactID) {
        messagesRepository.apiCallAndPutInDB(contactID);
    }

    public LiveData<List<Message>> getMessages(String contactID) {
        return messagesRepository.getMessages(contactID);
    }

    public void postMessage(String id, MessageRequest content) {
        messagesRepository.postMessage(id, content, messageSubmitted);
    }

    public LiveData<Boolean> isMessageSubmitted() {
        return messageSubmitted;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
