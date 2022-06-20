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

    /**
     * retrieves the messages with the contact from the server and puts the in the db
     * @param contactID
     */
    public void getMessagesFromApi(String contactID) {
        messagesRepository.apiCallAndPutInDB(contactID);
    }

    /**
     * gets the messages with the contact from the database
     * @param contactID
     * @return list of messages with the contact
     */
    public LiveData<List<Message>> getMessages(String contactID) {
        return messagesRepository.getMessages(contactID);
    }

    /**
     * posts the message to the user and transfer it to the contact
     * @param id
     * @param content
     */
    public void postMessage(String id, MessageRequest content) {
        messagesRepository.postMessage(id, content, messageSubmitted);
    }

    /**
     * checks if the message has been submitted successfully
     * @return
     */
    public LiveData<Boolean> isMessageSubmitted() {
        return messageSubmitted;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
