package com.example.valentino.rendezvous.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.valentino.rendezvous.listeners.EventListener;
import com.example.valentino.rendezvous.models.Event;

import java.util.ArrayList;
import java.util.List;

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
}
