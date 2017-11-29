package com.example.valentino.rendezvous.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.AddUsersActivity;
import com.example.valentino.rendezvous.adapters.FriendsAdapter;
import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.User;
import com.example.valentino.rendezvous.dao.UserDAO;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends Fragment implements FriendsAdapter.OnItemClickListener {
    private static final String TAG = FriendsListFragment.class.getSimpleName();

    List<User> friendsList;
    FriendsAdapter adapter;

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
	((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Friends");

	View view = inflater.inflate(R.layout.fragment_friends_list, container, false);
	// Inflate the layout for this fragment
	friendsList = new ArrayList<>();
	adapter = new FriendsAdapter(friendsList, null, this, false);
	UserDAO.getFriends(new UserListener() {
	    @Override
	    public void onSuccess(List<User> users) {
	        adapter.updateFriendsList(users);
	    }
	});
	LinearLayoutManager llm = new LinearLayoutManager(getContext());
	llm.setOrientation(LinearLayoutManager.VERTICAL);
	RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friendsRecyclerView);
	recyclerView.setAdapter(adapter);
	recyclerView.setLayoutManager(llm);
	recyclerView.setItemAnimator(new DefaultItemAnimator());
	return view;
    }

    @Override
    public void onItemClick(View view, User user) {
	FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	ProfileFragment profileFragment = new ProfileFragment();
	Bundle b = new Bundle();
	b.putSerializable("User", user);
	profileFragment.setArguments(b);
	ft.replace(R.id.content, profileFragment);
	ft.addToBackStack("userProfile");
	ft.commit();
    }
}
