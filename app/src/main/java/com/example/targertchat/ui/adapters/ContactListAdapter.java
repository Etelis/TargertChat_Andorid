package com.example.targertchat.ui.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.targertchat.R;
import com.example.targertchat.data.model.Contact;
import com.example.targertchat.ui.contacts.ContactDialogActivity;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private Context context;
    private final LayoutInflater mInflater;
    private List<Contact> contacts;
    public ContactListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvTitle;
        private final TextView tvDetails;
        private final TextView tvTime;
        private final ImageView imageView;
        private final RelativeLayout contactLayoutContainer;

        private ContactViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvName);
            tvDetails = itemView.findViewById(R.id.tvLastMsg);
            imageView = itemView.findViewById(R.id.profile_image);
            tvTime = itemView.findViewById(R.id.tvTime);
            contactLayoutContainer = itemView.findViewById(R.id.chat_row_container);

            imageView.setOnClickListener(view -> {
                Intent i = new Intent(new Intent(context, ContactDialogActivity.class));
                i.putExtra("url", contacts.get(getAdapterPosition()).getProfilePic());
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity)context, view, "transition");
                context.startActivity(i, options.toBundle());
            });
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);

            if(current == null){
                holder.contactLayoutContainer.setVisibility(View.INVISIBLE);
            }

            Glide.with(context)
                    .load(current.getProfilePic())
                    .apply(new RequestOptions().placeholder(R.drawable.profile))
                    .into(holder.imageView);
            holder.tvTitle.setText(current.getContactName());
            holder.tvDetails.setText(current.getLastMessage());
            holder.tvTime.setText(current.getLastSeen());
        }
    }

    public void setContacts(List<Contact> s){
        contacts = s;
        notifyDataSetChanged();
    }

    public void clear(){
        contacts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Contact> s){
        contacts.addAll(s);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(contacts != null)
            return contacts.size();
        else return 0;
    }

    public List<Contact> getContacts() {return contacts;}

}
