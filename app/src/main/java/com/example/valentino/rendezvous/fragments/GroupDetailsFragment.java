package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.adapters.AddUserAdapter;
import com.example.valentino.rendezvous.dao.GroupDAO;
import com.example.valentino.rendezvous.dao.UserDAO;
import com.example.valentino.rendezvous.listeners.GroupListener;
import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.Group;
import com.example.valentino.rendezvous.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupDetailsFragment extends Fragment {

    Group group;
    List<User> goingList;
    AddUserAdapter goingAdapter;
    RecyclerView goingRecyclerView;
    TextView nameField;

    public GroupDetailsFragment() {
	// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	if (getArguments() != null) {
	    group = (Group) getArguments().getSerializable("Group");
	}
	UserDAO.getFriendsFromIDList(new HashSet<String>(group.getUsers()), new UserListener() {
	    @Override
	    public void onSuccess(List<User> users) {
		goingList = users;
		goingAdapter.updateAddUserList(goingList);
	    }
	});

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Group Details");

	// Inflate the layout for this fragment
	View view = inflater.inflate(R.layout.fragment_group_details, container, false);

	nameField = (TextView) view.findViewById(R.id.groupNameField);
	nameField.setText(group.getName());

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(group.getName());

	goingAdapter = new AddUserAdapter(goingList);
	goingRecyclerView = (RecyclerView) view.findViewById(R.id.goingRecyclerView);
	LinearLayoutManager goingLayoutManager = new LinearLayoutManager(getContext());
	goingLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
	goingRecyclerView.setAdapter(goingAdapter);
	goingRecyclerView.setLayoutManager(goingLayoutManager);
	goingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }


}
