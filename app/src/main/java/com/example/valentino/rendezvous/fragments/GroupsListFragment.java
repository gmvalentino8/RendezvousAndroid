package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.CreateGroupActivity;

public class GroupsListFragment extends Fragment {
    private static final String TAG = GroupsListFragment.class.getSimpleName();
    public GroupsListFragment() {
	// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	View root = inflater.inflate(R.layout.fragment_groups_list, container, false);
	root.findViewById(R.id.fab).setOnClickListener(newGroup );
	return root;
    }

    View.OnClickListener newGroup = new View.OnClickListener() {
	@Override
	public void onClick(View view) {
	    Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
	    startActivityForResult(intent, 200);
	}
    };

}
