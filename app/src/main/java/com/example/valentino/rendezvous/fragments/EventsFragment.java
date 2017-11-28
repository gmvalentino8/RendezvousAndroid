package com.example.valentino.rendezvous.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.CreateEventActivity;
import com.example.valentino.rendezvous.activities.FilterEventActivity;
import com.example.valentino.rendezvous.adapters.EventsAdapter;
import com.example.valentino.rendezvous.listeners.EventListener;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.dao.EventDAO;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = EventsFragment.class.getSimpleName();
    public static final String EVENT_TYPE_KEY = "EventTypeKey";


    List<Event> eventsList = new ArrayList<>();
    EventsAdapter adapter;

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
	View view = inflater.inflate(R.layout.fragment_events, container, false);
    view.findViewById(R.id.filter).setOnClickListener(this);
    view.findViewById(R.id.fab).setOnClickListener(this);

    eventsList = new ArrayList<>();
    adapter = new EventsAdapter(eventsList);

    String eventsFilter = "None";
    if (getArguments() != null) {
        eventsFilter = getArguments().getString(EVENT_TYPE_KEY);
        EventDAO.getFilteredEvents(eventsFilter, new EventListener() {
            @Override
            public void onSuccess(List<Event> events) {
                adapter.updateEventsList(events);
            }
        });
    }
    else {
        EventDAO.getEvents(new EventListener() {
            @Override
            public void onSuccess(List<Event> events) {
                adapter.updateEventsList(events);
            }
        });
    }

    LinearLayoutManager llm = new LinearLayoutManager(getContext());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventsRecyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(llm);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

//	if (getArguments() != null) {
//	    eventType = getArguments().getString(EVENT_TYPE_KEY);
////	    TextView textview = (TextView) root.findViewById(R.id.test);
//	    if ((eventType.equals("Going") || eventType.equals("Invites"))) {
//		root.findViewById(R.id.fab).setVisibility(View.GONE);
//	    }
////	    textview.setText(eventType);
//	}
	return view;
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

