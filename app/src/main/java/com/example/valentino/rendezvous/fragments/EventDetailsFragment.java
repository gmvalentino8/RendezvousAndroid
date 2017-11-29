package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventDetailsFragment extends Fragment {

    Event event;

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
	    Toast.makeText(getContext(), event.getName(), Toast.LENGTH_SHORT).show();
	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        nameField = (TextView) view.findViewById(R.id.nameField);
        descriptionField = (TextView) view.findViewById(R.id.descriptionField);
        locationField = (TextView) view.findViewById(R.id.locationField);
        startDateField = (TextView) view.findViewById(R.id.startDate);
        endDateField = (TextView) view.findViewById(R.id.endDate);
        maxUsersField = (TextView) view.findViewById(R.id.maxGoingLabel);
        privateEventSwitch = (Switch) view.findViewById(R.id.privateSwitch);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        nameField.setText(event.getName());
        descriptionField.setText(event.getDescription());
        locationField.setText(event.getLocation());
        startDateField.setText(df.format(event.getStartDate()));
        endDateField.setText(df.format(event.getEndDate()));
        privateEventSwitch.setChecked(event.isPrivacy());


        return view;
    }

}
