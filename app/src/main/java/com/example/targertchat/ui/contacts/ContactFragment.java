package com.example.targertchat.ui.contacts;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.targertchat.R;
import com.example.targertchat.ui.adapters.ContactListAdapter;

/**
 * A fragment representing a list of Items.
 */
public class ContactFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ContactsViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider
                (this, new ContactsViewModelFactory()).get(ContactsViewModel.class);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_item_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refreshLayout);


        // Set the adapter
        if (recyclerView != null) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            final ContactListAdapter adapter = new ContactListAdapter(context);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            viewModel.getContactsFromAPI();
            viewModel.getContacts().observe(getViewLifecycleOwner(), adapter::setContacts);

            swipeRefreshLayout.setOnRefreshListener(() -> {
                adapter.clear();
                viewModel.getContacts();
                new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000); // Delay in millis
            });
        }
        return view;
    }
}