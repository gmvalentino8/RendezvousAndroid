package com.example.valentino.rendezvous.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.valentino.rendezvous.adapters.AddUserAdapter;
import com.example.valentino.rendezvous.dao.GroupDAO;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.Group;
import com.example.valentino.rendezvous.models.User;
import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.example.valentino.rendezvous.R;

import java.util.ArrayList;
import java.util.List;


public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;

    private Button createGroupButton;
    private Button addFriendsButton;
    private EditText groupNameField;
    private ImageView groupProfileImage;
    private List<User> inviteList;
    private List<String> userList;
    private AddUserAdapter inviteAdapter;
    private RecyclerView inviteRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_create_group);

	createGroupButton = (Button) findViewById(R.id.createGroupButton);
	groupNameField = (EditText) findViewById(R.id.groupNameField);
	groupProfileImage = (ImageView) findViewById(R.id.groupImageView);
	createGroupButton.setOnClickListener(this);
	groupProfileImage.setOnClickListener(this);
	inviteList = new ArrayList<>();
	inviteAdapter = new AddUserAdapter(inviteList);
	inviteRecyclerView = (RecyclerView) findViewById(R.id.invitesRecyclerView);
	LinearLayoutManager inviteLayoutManager = new LinearLayoutManager(this);
	inviteLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
	inviteRecyclerView.setAdapter(inviteAdapter);
	inviteRecyclerView.setLayoutManager(inviteLayoutManager);
	inviteRecyclerView.setItemAnimator(new DefaultItemAnimator());
	addFriendsButton = (Button) findViewById(R.id.addFriendsButton);
	addFriendsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
	switch (view.getId()){
	    case R.id.groupImageView:
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
		break;
	    case R.id.addFriendsButton:
		Intent intent = new Intent(this, AddUsersActivity.class);
		startActivityForResult(intent, 100);
		break;
	    case R.id.createGroupButton:
	        userList = new ArrayList<>();
	        userList.add(Profile.getCurrentProfile().getId());
		for (User user : inviteList) {
		    userList.add(user.getId());
		}
	        Group newGroup = new Group(groupNameField.getText().toString(), "", userList);
		GroupDAO.createGroup(newGroup);
		finish();
		break;
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
	    Uri selectedImage =  data.getData();
	    groupProfileImage.setImageURI(selectedImage);
	}
	else if (requestCode == 100
		 && resultCode == Activity.RESULT_OK) {
	    inviteList = (List<User>) data.getSerializableExtra("UserList");
	    inviteAdapter.updateAddUserList(inviteList);
	}
    }
}