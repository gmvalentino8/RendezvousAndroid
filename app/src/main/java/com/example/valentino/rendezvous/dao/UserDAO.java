package com.example.valentino.rendezvous.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.User;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Valentino on 11/16/17.
 */

public class UserDAO {
    public static void getFriends(final UserListener listener) {
	FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
	Query eventsQuery = mDatabase.getReference("android_users").child(Profile.getCurrentProfile().getId()).child("friends");
	eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
	    @Override
	    public void onDataChange(DataSnapshot dataSnapshot) {
		Set<String> idList = new HashSet<>();
		for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
		    String userID = eventSnapshot.getValue().toString();
		    idList.add(userID);
		}
		getFriendsFromIDList(idList, listener);
	    }

	    @Override
	    public void onCancelled(DatabaseError databaseError) {

	    }
	});
    }

    public static void getFriendsFromIDList(final Set<String> idList, final UserListener listener) {
	FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
	Query eventsQuery = mDatabase.getReference("android_users");
	eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
	    @Override
	    public void onDataChange(DataSnapshot dataSnapshot) {
		List<User> friendsList = new ArrayList<>();
		for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
		    if (idList.contains(eventSnapshot.getKey())) {
			User friend = eventSnapshot.getValue(User.class);
			friendsList.add(friend);
		    }
		}
		listener.onSuccess(friendsList);
	    }

	    @Override
	    public void onCancelled(DatabaseError databaseError) {

	    }
	});
    }


}
