package com.example.valentino.rendezvous.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddUsersActivity extends AppCompatActivity implements View.OnClickListener {

    List<User> displayList;
    Set<User> displaySet;
    AddUserAdapter displayAdapter;
    RecyclerView displayRecyclerView;
    List<User> selectList;
    FriendsAdapter selectAdapter;
    RecyclerView selectRecyclerView;
    Button saveButton;
    MaterialSearchView searchView;
    Toolbar toolbar;

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
	selectAdapter = new FriendsAdapter(selectList, displaySet, new UserTouchListener(), true);
	selectRecyclerView = (RecyclerView) findViewById(R.id.selectFriendsRecyclerView);
	UserDAO.getFriends(new UserListener() {
	    @Override
	    public void onSuccess(List<User> users) {
	        selectList = users;
		selectAdapter.updateFriendsList(selectList);
	    }
	});
	LinearLayoutManager selectLayoutManager = new LinearLayoutManager(this);
	selectLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	selectRecyclerView.setAdapter(selectAdapter);
	selectRecyclerView.setLayoutManager(selectLayoutManager);
	selectRecyclerView.setItemAnimator(new DefaultItemAnimator());

	saveButton = (Button) findViewById(R.id.saveButton);
	saveButton.setOnClickListener(this);

	toolbar = (Toolbar) findViewById(R.id.topToolBar);
	toolbar.setTitle("Add Friends");
	toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
	setSupportActionBar(toolbar);


	searchView = (MaterialSearchView) findViewById(R.id.search_view);
	searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
	    @Override
	    public boolean onQueryTextSubmit(String query) {
		//Do some magic
		return false;
	    }

	    @Override
	    public boolean onQueryTextChange(String newText) {
		if (newText != null && !newText.isEmpty()) {
		    List<User> searchUserList = new ArrayList<>();
		    for (User item : selectList) {
		        if ((item.getFirstName()+item.getLastName()).toLowerCase().contains(newText.toLowerCase())) {
		            searchUserList.add(item);
			}
		    }
		    FriendsAdapter searchAdapter = new FriendsAdapter(searchUserList, displaySet, new UserTouchListener(), true);
		    selectRecyclerView.setAdapter(searchAdapter);
		} else {
		    FriendsAdapter selectAdapter = new FriendsAdapter(selectList, displaySet, new UserTouchListener(), true);
		    selectRecyclerView.setAdapter(selectAdapter);
		}
		return true;
	    }
	});

	searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
	    @Override
	    public void onSearchViewShown() {
		//Do some magic
	    }

	    @Override
	    public void onSearchViewClosed() {
		selectRecyclerView.setAdapter(selectAdapter);
	    }
	});
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu_search, menu);

	MenuItem item = menu.findItem(R.id.action_search);
	searchView.setMenuItem(item);
	return true;

    }

    public class UserTouchListener implements FriendsAdapter.OnItemClickListener {
	@Override
	public void onItemClick(View view, User user) {
	    if (!displaySet.contains(user)) {
		displayList.add(user);
		displaySet.add(user);
		ImageView icon = ((ImageView) selectRecyclerView.findContainingViewHolder(view).itemView.findViewById(R.id.addIndicatorImage));
		icon.setImageResource(R.drawable.ic_check_black);
		icon.setColorFilter(getApplicationContext().getResources().getColor(R.color.green));
	    } else {
		displayList.remove(user);
		displaySet.remove(user);
		ImageView icon = ((ImageView) selectRecyclerView.findContainingViewHolder(view).itemView.findViewById(R.id.addIndicatorImage));
		icon.setImageResource(R.drawable.ic_add_black);
		icon.setColorFilter(getApplicationContext().getResources().getColor(R.color.colorAccent));
	    }
	    displayAdapter.updateAddUserList(displayList);
	}
    }
}