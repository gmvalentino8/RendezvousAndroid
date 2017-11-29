package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.Group;

public class GroupDetailsFragment extends Fragment {

    Group group;

    TextView nameField;

    public GroupDetailsFragment() {
	// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	if (getArguments() != null) {
	    group = (Group) getArguments().getSerializable("Group");
	    Toast.makeText(getContext(), group.getName(), Toast.LENGTH_SHORT).show();
	}
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

        return view;
    }


}
