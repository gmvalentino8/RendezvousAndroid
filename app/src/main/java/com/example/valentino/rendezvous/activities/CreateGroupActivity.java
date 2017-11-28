package com.example.valentino.rendezvous.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.valentino.rendezvous.models.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.example.valentino.rendezvous.R;


public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    private DatabaseReference mDatabase;

    private Button createGroupButton;
    private EditText groupNameField;
    private ImageView groupProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_create_group);

	mDatabase = FirebaseDatabase.getInstance().getReference();

	createGroupButton = (Button) findViewById(R.id.createGroupButton);
	groupNameField = (EditText) findViewById(R.id.groupNameField);
	groupProfileImage = (ImageView) findViewById(R.id.groupImageView);
	createGroupButton.setOnClickListener(this);
	groupProfileImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
	switch (view.getId()){
	    case R.id.groupImageView:
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
		break;
	    case R.id.createGroupButton:
	        Group newGroup = new Group(groupNameField.getText().toString(), "", null);
	        String groupID = mDatabase.child("android_groups").push().getKey();
	        mDatabase.child("android_groups").child(groupID).setValue(newGroup);
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
    }
}