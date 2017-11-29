package com.example.valentino.rendezvous.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;

import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Valentino on 11/16/17.
 */

public class UserDAO {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void setUserData(AccessToken accessToken) {
	GraphRequest request = GraphRequest.newMeRequest(
	    accessToken,
	    new GraphRequest.GraphJSONObjectCallback() {
		@Override
		public void onCompleted(
		    JSONObject object,
		    GraphResponse response) {
		    try {
			String firstName = object.getString("first_name");
			String lastName = object.getString("last_name");
			String email = object.getString("email");
			String picture = object.getJSONObject("picture").getJSONObject("data").getString("url");
			String facebookID = object.getString("id");
			Map<String, Object> userUpdate = new HashMap<>();
			userUpdate.put("firstName", firstName);
			userUpdate.put("lastName", lastName);
			userUpdate.put("email", email);
			userUpdate.put("picture", picture);
			mDatabase.child("android_users").child(facebookID).updateChildren(userUpdate);
		    }
		    catch (JSONException e) {
			e.printStackTrace();
		    }
		}
	    });
	Bundle parameters = new Bundle();
	parameters.putString("fields", "id, first_name, last_name, email, picture.type(large).width(960).height(960)");
	request.setParameters(parameters);
	request.executeAsync();
    }

    public static void setFriendsList() {
	final List<String> friendslist = new ArrayList<String>();
	new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
	    public void onCompleted(GraphResponse response) {
		try {
		    JSONObject responseObject = response.getJSONObject();
		    JSONArray dataArray = responseObject.getJSONArray("data");

		    for (int i = 0; i < dataArray.length(); i++) {
			JSONObject dataObject = dataArray.getJSONObject(i);
			String fbId = dataObject.getString("id");
			friendslist.add(fbId);
		    }
		    List<String> list = friendslist;
		    mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("friends").setValue(list);
		} catch (JSONException e) {
		    e.printStackTrace();
		}
	    }
	}).executeAsync();
    }

    public static void getFriends(final UserListener listener) {
	Query eventsQuery = mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("friends");
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
	Query eventsQuery = mDatabase.child("android_users");
	eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
	    @Override
	    public void onDataChange(DataSnapshot dataSnapshot) {
		List<User> friendsList = new ArrayList<>();
		for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
		    if (idList.contains(eventSnapshot.getKey())) {
			User friend = eventSnapshot.getValue(User.class);
			friend.setId(eventSnapshot.getKey());
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

    public static void getUserProfile(String userID, final UserListener listener) {
	Query eventsQuery = mDatabase.child("android_users").child(userID);
	eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
	    @Override
	    public void onDataChange(DataSnapshot dataSnapshot) {
		List<User> userList = new ArrayList<>();
		User user = dataSnapshot.getValue(User.class);
		user.setId(dataSnapshot.getKey());
		userList.add(user);
		listener.onSuccess(userList);
	    }

	    @Override
	    public void onCancelled(DatabaseError databaseError) {

	    }
	});
    }


}
