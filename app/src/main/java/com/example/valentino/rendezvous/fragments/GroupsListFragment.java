package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.CreateGroupActivity;
import com.example.valentino.rendezvous.adapters.FriendsAdapter;
import com.example.valentino.rendezvous.adapters.GroupsAdapter;
import com.example.valentino.rendezvous.dao.GroupDAO;
import com.example.valentino.rendezvous.listeners.GroupListener;
import com.example.valentino.rendezvous.models.Group;
import com.example.valentino.rendezvous.models.User;

import java.util.ArrayList;
import java.util.List;

public class GroupsListFragment extends Fragment {
    private static final String TAG = GroupsListFragment.class.getSimpleName();

    List<Group> groupsList;
    GroupsAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_groups_list, container, false);
        view.findViewById(R.id.fab).setOnClickListener(newGroup );

        groupsList = new ArrayList<>();
        adapter = new GroupsAdapter(groupsList);

        GroupDAO.getGroups(new GroupListener() {
            @Override
            public void onSuccess(List<Group> groups) {
                adapter.updateGroupsList(groups);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.groupsRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    View.OnClickListener newGroup = new View.OnClickListener() {
	@Override
	public void onClick(View view) {
	    Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
	    startActivityForResult(intent, 200);
	}
    };

}
