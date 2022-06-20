package com.example.targertchat.data.remote;

import android.app.NotificationChannel;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.targertchat.R;
import com.example.targertchat.data.model.Message;
import com.example.targertchat.data.repositories.ContactsRepository;
import com.example.targertchat.data.repositories.MessagesRepository;
import com.example.targertchat.data.utils.NotificationMessageUpdate;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * NotificationManager - Handle notifications received from Firebase.
 */
public class NotificationManager extends FirebaseMessagingService {
    public NotificationManager() {
    }

    /**
     * onMessageReceived - Handle new notification received from Firebase module.
     * @param messageRecived
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage messageRecived) {
        if (messageRecived.getNotification() != null) {
            // Create new notification to device.
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.
                    Builder(this, "1")
                    .setSmallIcon(R.drawable.goal)
                    .setContentTitle(messageRecived.getNotification().getTitle())
                    .setContentText(messageRecived.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());

            // Parse received message by fields.
            String contactID = messageRecived.getData().get("contactID");
            String content = messageRecived.getData().get("content");
            String created = messageRecived.getData().get("created");

            // Update appropriate contact with pushed message content and date.
            NotificationMessageUpdate notificationUpdate = new NotificationMessageUpdate(contactID, content, created);
            ContactsRepository.getInstance().updateContactOnNewMessage(notificationUpdate);

            // Push received message to dao.
            Message message = new Message(content, created, "true", contactID);
            MessagesRepository.getInstance().pushMessageToDAO(message);
        }
    }

    /**
     * Notification creation handling.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "myChannel", importance);
            channel.setDescription("Demo Channel");
            android.app.NotificationManager notificationManager = getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}