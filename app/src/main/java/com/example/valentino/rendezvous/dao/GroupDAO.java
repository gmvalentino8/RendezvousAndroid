package com.example.valentino.rendezvous.dao;

import com.example.valentino.rendezvous.listeners.GroupListener;
import com.example.valentino.rendezvous.models.Group;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Valentino on 11/16/17.
 */

public class GroupDAO {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void createGroup(final Group group) {
        String groupID = mDatabase.child("android_groups").push().getKey();
        mDatabase.child("android_groups").child(groupID).setValue(group);
        for (String userID : group.getUsers()) {
            setGroupMembers(groupID, userID);
        }
    }

    public static void setGroupMembers(String groupID, String userID) {
        mDatabase.child("android_users").child(userID).child("groups").child(groupID).setValue(true);
    }

    public static void getGroups(final GroupListener listener) {
        Query eventsQuery = mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("groups");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> idList = new HashSet<>();
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    String groupID = eventSnapshot.getKey();
                    idList.add(groupID);
                }
                getGroupsFromIDList(idList, listener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getGroupsFromIDList(final Set<String> idList, final GroupListener listener) {
        Query eventsQuery = mDatabase.child("android_groups").orderByChild("name");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Group> groupsList = new ArrayList<>();
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    if (idList.contains(eventSnapshot.getKey())) {
                        Group group = eventSnapshot.getValue(Group.class);
                        groupsList.add(group);
                    }
                }
                listener.onSuccess(groupsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
