package com.example.valentino.rendezvous.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.adapters.AddUserAdapter;
import com.example.valentino.rendezvous.adapters.FriendsAdapter;
import com.example.valentino.rendezvous.dao.UserDAO;
import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddUsersActivity extends AppCompatActivity implements FriendsAdapter.OnItemClickListener,
								   View.OnClickListener {

    List<User> displayList;
    Set<User> displaySet;
    AddUserAdapter displayAdapter;
    RecyclerView displayRecyclerView;
    List<User> selectList;
    FriendsAdapter selectAdapter;
    RecyclerView selectRecyclerView;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_add_users);

	displayList = new ArrayList<>();
	displaySet = new HashSet<>();
	displayAdapter = new AddUserAdapter(displayList);
	displayRecyclerView = (RecyclerView) findViewById(R.id.addUserRecyclerView);
	LinearLayoutManager displayLayoutManager = new LinearLayoutManager(this);
	displayLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
	displayRecyclerView.setAdapter(displayAdapter);
	displayRecyclerView.setLayoutManager(displayLayoutManager);
	displayRecyclerView.setItemAnimator(new DefaultItemAnimator());

	selectList = new ArrayList<>();
	selectAdapter = new FriendsAdapter(selectList, this, true);
	selectRecyclerView = (RecyclerView) findViewById(R.id.selectFriendsRecyclerView);
	UserDAO.getFriends(new UserListener() {
	    @Override
	    public void onSuccess(List<User> users) {
		selectAdapter.updateFriendsList(users);
	    }
	});
	LinearLayoutManager selectLayoutManager = new LinearLayoutManager(this);
	selectLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	selectRecyclerView.setAdapter(selectAdapter);
	selectRecyclerView.setLayoutManager(selectLayoutManager);
	selectRecyclerView.setItemAnimator(new DefaultItemAnimator());

	saveButton = (Button) findViewById(R.id.saveButton);
	saveButton.setOnClickListener(this);
    }

    @Override
    public void onItemClick(View view, User user) {
        if (!displaySet.contains(user)) {
	    displayList.add(user);
	    displaySet.add(user);
	}
	else {
	    displayList.remove(user);
	    displaySet.remove(user);
	}
	displayAdapter.updateAddUserList(displayList);
    }

    @Override
    public void onClick(View view) {
	switch (view.getId()) {
	    case R.id.saveButton:
		Intent resultIntent = new Intent();
		Bundle b = new Bundle();
		b.putSerializable("UserList", (Serializable) displayList);
		resultIntent.putExtras(b);
		setResult(Activity.RESULT_OK, resultIntent);
	        finish();
	}
    }
}
