package com.example.valentino.rendezvous.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.CreateGroupActivity;
import com.example.valentino.rendezvous.adapters.GroupsAdapter;
import com.example.valentino.rendezvous.dao.GroupDAO;
import com.example.valentino.rendezvous.listeners.GroupListener;
import com.example.valentino.rendezvous.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupsListFragment extends Fragment implements GroupsAdapter.OnItemClickListener {
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
        adapter = new GroupsAdapter(groupsList, this);

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

    @Override
    public void onItemClick(View view, Group group) {
        FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        GroupDetailsFragment detailsFragment = new GroupDetailsFragment();
        Bundle b = new Bundle();
        b.putSerializable("Group", group);
        detailsFragment.setArguments(b);
        ft.replace(R.id.content, detailsFragment);
        ft.addToBackStack("groupDetails");
        ft.commit();
    }
}
