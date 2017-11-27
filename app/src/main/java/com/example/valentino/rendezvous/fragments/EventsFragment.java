package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.CreateEventActivity;
import com.example.valentino.rendezvous.activities.FilterEventActivity;
import com.example.valentino.rendezvous.activities.MainActivity;

public class EventsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = EventsFragment.class.getSimpleName();
    public static final String EVENT_TYPE_KEY = "EventTypeKey";
    private String eventType;

    public EventsFragment() {
	// Required empty public constructor
    }

    public static EventsFragment newInstance(Bundle args) {
	EventsFragment fragment = new EventsFragment();
	fragment.setArguments(args);
	return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	View root = inflater.inflate(R.layout.fragment_events, container, false);
	root.findViewById(R.id.filter).setOnClickListener(this);
	root.findViewById(R.id.fab).setOnClickListener(this);
	if (getArguments() != null) {
	    eventType = getArguments().getString(EVENT_TYPE_KEY);
	    TextView textview = (TextView) root.findViewById(R.id.test);
	    if ((eventType.equals("Going") || eventType.equals("Invites"))) {
		root.findViewById(R.id.fab).setVisibility(View.GONE);
	    }
	    textview.setText(eventType);
	}
	return root;
    }

    @Override
    public void onClick(View view) {
	switch (view.getId()) {
	    case R.id.fab:
		Intent intent = new Intent(getActivity(), CreateEventActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivityForResult(intent, 100);
		break;
	    case R.id.filter:
		Intent intent2 = new Intent(getActivity(), FilterEventActivity.class);
		startActivityForResult(intent2, 200);
	}
    }
}

