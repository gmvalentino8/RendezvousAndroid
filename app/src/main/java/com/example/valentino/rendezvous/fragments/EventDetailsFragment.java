package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.adapters.AddUserAdapter;
import com.example.valentino.rendezvous.dao.UserDAO;
import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

public class EventDetailsFragment extends Fragment {

    Event event;

    List<User> goingList;
    AddUserAdapter goingAdapter;
    RecyclerView goingRecyclerView;

    TextView nameField;
    TextView descriptionField;
    TextView locationField;
    TextView startDateField;
    TextView endDateField;
    TextView maxUsersField;
    Switch privateEventSwitch;

    public EventDetailsFragment() {
	// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	if (getArguments() != null) {
	    event = (Event) getArguments().getSerializable("Event");
	}

        UserDAO
            .getFriendsFromIDList(event.getUsers().keySet(), new UserListener() {
                @Override
                public void onSuccess(List<User> users) {
                    goingList = users;
                    goingAdapter.updateAddUserList(goingList);
                }
            });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Event Details");

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        nameField = (TextView) view.findViewById(R.id.nameField);
        descriptionField = (TextView) view.findViewById(R.id.descriptionField);
        locationField = (TextView) view.findViewById(R.id.locationField);
        startDateField = (TextView) view.findViewById(R.id.startDate);
        endDateField = (TextView) view.findViewById(R.id.endDate);
        maxUsersField = (TextView) view.findViewById(R.id.maxGoingLabel);
        privateEventSwitch = (Switch) view.findViewById(R.id.privateSwitch);

        DateFormat df = new SimpleDateFormat("MMM dd; hh:mm a");

        nameField.setText(event.getName());
        descriptionField.setText(event.getDescription());
        locationField.setText(event.getLocation());
        startDateField.setText(df.format(event.getStartDate()));
        endDateField.setText(df.format(event.getEndDate()));
        privateEventSwitch.setChecked(event.isPrivacy());

        goingAdapter = new AddUserAdapter(goingList);
        goingRecyclerView = (RecyclerView) view.findViewById(R.id.goingRecyclerView);
        LinearLayoutManager goingLayoutManager = new LinearLayoutManager(getContext());
        goingLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        goingRecyclerView.setAdapter(goingAdapter);
        goingRecyclerView.setLayoutManager(goingLayoutManager);
        goingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
