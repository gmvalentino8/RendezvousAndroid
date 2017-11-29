package com.example.valentino.rendezvous.dao;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.valentino.rendezvous.listeners.EventListener;
import com.example.valentino.rendezvous.models.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Valentino on 11/16/17.
 */

public class EventDAO {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void getFilteredEvents(final String eventsFilter, final EventListener listener) {
	Query eventsQuery = mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("events");
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
	Query eventsQuery = mDatabase.child("android_events").orderByChild("startDate");
	eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
	    @Override
	    public void onDataChange(DataSnapshot dataSnapshot) {
		List<Event> eventsList = new ArrayList<>();
		for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
		    if (idList.contains(eventSnapshot.getKey())) {
			Event event = eventSnapshot.getValue(Event.class);
			event.setId(eventSnapshot.getKey());
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

    public static void getPublicEvents(final EventListener listener) {
        Query eventsQuery = mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("events");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> idList = new HashSet<>();
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    String eventID = eventSnapshot.getKey();
                    // We don't want to display events that the user is hosting
                    if(!(eventSnapshot.getValue().toString().equals("Hosting") || eventSnapshot.getValue().toString().equals("Going"))) {
                        idList.add(eventID);
                    }
                }
                getPublicEventsFromIDList(idList, listener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getPublicEventsFromIDList(final Set<String> idList, final EventListener listener) {
        Query eventsQuery = mDatabase.child("android_events").orderByChild("startDate");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Event> eventsList = new ArrayList<>();
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    if (idList.contains(eventSnapshot.getKey())) {
                        Event event = eventSnapshot.getValue(Event.class);
                        event.setId(eventSnapshot.getKey());
                        // We don't want to display private events here
                        if(!event.isPrivacy()) {
                            eventsList.add(event);
                        }
                    }
                }
                listener.onSuccess(eventsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void createEvent(final Event event) {
	String eventID = mDatabase.child("android_events").push().getKey();
	setEvent(eventID, event);
	for (Map.Entry<String, Object> entry : event.getUsers().entrySet()) {
	    setEventInvite(eventID, entry.getKey());
	}
	setEventHosting(eventID);
    }

    public static void setEvent(final String eventID, final Event event) {
	mDatabase.child("android_events").child(eventID).setValue(event);
    }

    public static void setEventHosting(final String eventID) {
	mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("events").child(eventID).setValue("Hosting");
	mDatabase.child("android_events").child(eventID).child("users").child(Profile.getCurrentProfile().getId()).setValue("Hosting");
    }

    public static void setEventGoing(final String eventID) {
	mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("events").child(eventID).setValue("Going");
	mDatabase.child("android_events").child(eventID).child("users").child(Profile.getCurrentProfile().getId()).setValue("Going");
    }

    public static void setEventInvite(final String eventID, final String userID) {
	mDatabase.child("android_users").child(userID).child("events").child(eventID).setValue("Invited");
	mDatabase.child("android_events").child(eventID).child("users").child(userID).setValue("Invited");
    }

    public static void setEventDecline(final String eventID) {
	mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("events").child(eventID).removeValue();
	mDatabase.child("android_events").child(eventID).child("users").child(Profile.getCurrentProfile().getId()).removeValue();
    }


}
