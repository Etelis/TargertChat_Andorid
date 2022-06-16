package com.example.targertchat.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.targertchat.R;
import com.example.targertchat.data.model.Message;
import java.util.List;


public class MessagesListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_SENT = 1;
    private static final int VIEW_RECEIVED = 2;

    private Context context;
    private List<Message> messages;

    public MessagesListAdapter(Context newContext, List<Message> messageList) {
        context = newContext;
        messages = messageList;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) messages.get(position);

        if (message.getSent().equals("false")) {
            // If the current user is the sender of the message
            return VIEW_SENT;
        } else {
            // If some other user sent the message
            return VIEW_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message_item, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.received_message_item, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }
    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.sent_text);
            timeText = (TextView) itemView.findViewById(R.id.sent_time);
        }

        public void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText((message.getCreated()));
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.received_text);
            timeText = (TextView) itemView.findViewById(R.id.received_time);
        }

        public void bind(Message message) {
            messageText.setText(message.getContent());
            timeText.setText((message.getCreated()));
        }
    }
}