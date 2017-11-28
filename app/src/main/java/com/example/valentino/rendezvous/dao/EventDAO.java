package com.example.valentino.rendezvous.dao;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.valentino.rendezvous.listeners.EventListener;
import com.example.valentino.rendezvous.models.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Valentino on 11/16/17.
 */

public class EventDAO {
    public static void getEvents(final EventListener listener) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        Query eventsQuery = mDatabase.getReference("android_events");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
		@Override
	    public void onDataChange(DataSnapshot dataSnapshot) {
			List<Event> list = new ArrayList<>();
			for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
				Event event = eventSnapshot.getValue(Event.class);
				list.add(event);
			}
			listener.onSuccess(list);
	    }

	    @Override
	    public void onCancelled(DatabaseError databaseError) {

	    }
	    });
    }

    public static void getFilteredEvents(final String eventsFilter, final EventListener listener) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        Query eventsQuery = mDatabase.getReference("android_users").child(Profile.getCurrentProfile().getId()).child("events");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> idList = new HashSet<>();
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    String eventID = eventSnapshot.getKey();
                    if(eventSnapshot.getValue().toString().equals(eventsFilter)) {
                        idList.add(eventID);
                    }
                }
                getEventsFromIDList(idList, listener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getEventsFromIDList(final Set<String> idList, final EventListener listener) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        Query eventsQuery = mDatabase.getReference("android_events").orderByChild("startDate");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Event> eventsList = new ArrayList<>();
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    if (idList.contains(eventSnapshot.getKey())) {
                        Event event = eventSnapshot.getValue(Event.class);
                        eventsList.add(event);
                    }
                }
                listener.onSuccess(eventsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
