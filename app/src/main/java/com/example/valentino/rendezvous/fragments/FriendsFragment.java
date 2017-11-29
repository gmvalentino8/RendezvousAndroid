package com.example.valentino.rendezvous.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;

public class FriendsFragment extends Fragment {
    private static final String TAG = FriendsFragment.class.getSimpleName();

    public FriendsFragment() {
	// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Friends");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	// Inflate the layout for this fragment
	View root = inflater.inflate(R.layout.fragment_friends, container, false);

	FragmentTabHost tabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
	tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
	tabHost.addTab(tabHost.newTabSpec("FriendsList").setIndicator("Friends"),
		       FriendsListFragment.class, null);
	tabHost.addTab(tabHost.newTabSpec("GroupsList").setIndicator("Groups"),
		       GroupsListFragment.class, null);

	return root;
    }

}
