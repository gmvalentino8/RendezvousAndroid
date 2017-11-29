package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.User;

public class EventDetailsFragment extends Fragment {

    Event event;

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
	return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

}
