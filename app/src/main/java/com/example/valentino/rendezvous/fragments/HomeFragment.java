package com.example.valentino.rendezvous.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
	// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	View root = inflater.inflate(R.layout.fragment_home, container, false);

	FragmentTabHost tabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
	/** Important: Must use child FragmentManager. */
	tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

	Bundle arg1 = new Bundle();
	arg1.putString(EventsFragment.EVENT_TYPE_KEY, "Going");
	tabHost.addTab(tabHost.newTabSpec("GoingTab").setIndicator("Going"),
		       EventsFragment.class, arg1);

	Bundle arg2 = new Bundle();
	arg2.putString(EventsFragment.EVENT_TYPE_KEY, "Hosting");
	tabHost.addTab(tabHost.newTabSpec("HostingTab").setIndicator("Hosting"),
		       EventsFragment.class, arg2);

	Bundle arg3 = new Bundle();
	arg3.putString(EventsFragment.EVENT_TYPE_KEY, "Invited");
	tabHost.addTab(tabHost.newTabSpec("InvitesTab").setIndicator("Invited"),
		       EventsFragment.class, arg3);
	return root;
    }

}
