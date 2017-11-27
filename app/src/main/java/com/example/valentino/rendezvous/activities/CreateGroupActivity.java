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

import com.example.valentino.rendezvous.models.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.example.valentino.rendezvous.R;


public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    private DatabaseReference mDatabase;

    private Button mFirebasebtn;
    private ImageButton GroupImage;
    private Button Friendbutton;
    final String group_string = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_create_group);
	mFirebasebtn = (Button) findViewById(R.id.Firebasebtn);
	GroupImage = (ImageButton) findViewById(R.id.groupimage);
	Friendbutton = (Button) findViewById(R.id.friends);

	mDatabase = FirebaseDatabase.getInstance().getReference();

	EditText groupname = (EditText) findViewById(R.id.group_name);
	final String group_string = groupname.getText().toString();

	mFirebasebtn.setOnClickListener(this);
	GroupImage.setOnClickListener(this);
	Friendbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
	switch (view.getId()){
	    case R.id.Firebasebtn:
		mDatabase.child("groupname").setValue(group_string);
		break;
	    case R.id.groupimage:
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
		break;
	    case R.id.friends:
		//start new fragment
		break;
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
	    Uri selectedImage =  data.getData();
	    GroupImage.setImageURI(selectedImage);
	}
    }
}