package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;

public class FriendsListFragment extends Fragment {
    private static final String TAG = FriendsListFragment.class.getSimpleName();

    public FriendsListFragment() {
	// Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	// Inflate the layout for this fragment
	return inflater.inflate(R.layout.fragment_friends_list, container, false);
    }
}
