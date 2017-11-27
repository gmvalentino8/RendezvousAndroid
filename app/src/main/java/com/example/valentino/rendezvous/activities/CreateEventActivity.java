package com.example.valentino.rendezvous.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.models.Group;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;


import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.User;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CreateEventActivity.class.getSimpleName();
    private DatabaseReference mDatabase;
    final int REQUEST_PLACE_PICKER = 1;
    final int REQUEST_ADD_USERS = 2;

    private Button createButton;
    private Button addFriendsButton;
    private Switch privateSwitch;
    private EditText nameField;
    private EditText descriptionField;
    private EditText locationField;
    private EditText startDateField;
    private EditText endDateField;
    private EditText startTimeField;
    private EditText endTimeField;
    private EditText maxGoingField;
    private LatLng locationLatLng;
    private List<User> friendsList;
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_create_event);

	mDatabase = FirebaseDatabase.getInstance().getReference();
	createButton = (Button) findViewById(R.id.createButton);
	addFriendsButton = (Button) findViewById(R.id.addFriends);
	nameField = (EditText) findViewById(R.id.nameField);
	descriptionField = (EditText) findViewById(R.id.descriptionField);
	privateSwitch = (Switch) findViewById(R.id.privateSwitch);
	locationField = (EditText) findViewById(R.id.locationField);
	locationField.setFocusable(false);
	locationField.setClickable(true);
	locationField.setOnClickListener(this);
	startDateField = (EditText) findViewById(R.id.startDate);
	startDateField.setFocusable(false);
	startDateField.setClickable(true);
	startDateField.setOnClickListener(this);
	endDateField = (EditText) findViewById(R.id.endDate);
	endDateField.setFocusable(false);
	endDateField.setClickable(true);
	endDateField.setOnClickListener(this);
	startTimeField = (EditText) findViewById(R.id.startTime);
	startTimeField.setFocusable(false);
	startTimeField.setClickable(true);
	startTimeField.setOnClickListener(this);
	endTimeField = (EditText) findViewById(R.id.endTime);
	endTimeField.setFocusable(false);
	endTimeField.setClickable(true);
	endTimeField.setOnClickListener(this);
	maxGoingField = (EditText) findViewById(R.id.maxGoingField);
	
	createButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
	switch (view.getId()){
	    case R.id.createButton:
		Event newEvent = new Event(nameField.getText().toString(), descriptionField.getText().toString(),
					   locationField.getText().toString(), locationLatLng,
					   privateSwitch.isChecked(), startDate.getTime(), endDate.getTime(), 1, null);
		String eventID = mDatabase.child("android_events").push().getKey();
		mDatabase.child("android_events").child(eventID).setValue(newEvent);
		mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("events").child(eventID).setValue(Event.Status.Hosting.toString());
		Toast.makeText(this, "Event Created", Toast.LENGTH_SHORT).show();
		finish();
	        break;
	    case R.id.locationField:
		onPickButtonClick();
		break;
	    case R.id.startDate:
		getDate(startDate, startDateField);
		if (endDate.before(startDate)) {
		    endDate = startDate;
		}
	        break;
	    case R.id.endDate:
	        getDate(endDate, endDateField);
	        break;
	    case R.id.startTime:
	        getTime(startDate, startTimeField);
	        break;
	    case R.id.endTime:
	        getTime(endDate, endTimeField);
	        break;
	    case R.id.addFriends:
		Intent intent = new Intent(this, AddUsersActivity.class);
		startActivityForResult(intent, REQUEST_ADD_USERS);
	}
    }

    @Exclude
    public void getDate(final Calendar dateCalendar, final EditText label) {
	final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
	    @Override
	    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
		dateCalendar.set(Calendar.YEAR, year);
		dateCalendar.set(Calendar.MONTH, month);
		dateCalendar.set(Calendar.DAY_OF_MONTH, day);
		setDateLabel(dateCalendar, label);
	    }
	};

	new DatePickerDialog(this, date, startDate
	    .get(Calendar.YEAR), startDate.get(Calendar.MONTH),
			     startDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setDateLabel(final Calendar date, EditText label) {
	String myFormat = "MMM dd, yyyy"; //In which you need put here
	SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	label.setText(sdf.format(date.getTime()));
    }

    @Exclude
    public void getTime(final Calendar dateCalendar, final EditText label) {
	final TimePickerDialog.OnTimeSetListener date = new TimePickerDialog.OnTimeSetListener() {
	    @Override
	    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
		dateCalendar.set(Calendar.HOUR_OF_DAY, hour);
		dateCalendar.set(Calendar.MINUTE, minute);
		setTimeLabel(dateCalendar, label);
	    }
	};

	new TimePickerDialog(this, date, startDate
	    .get(Calendar.HOUR_OF_DAY), startDate.get(Calendar.MINUTE),
			     false).show();
    }

    public void setTimeLabel(final Calendar date, EditText label) {
	String myFormat = "hh:mm a"; //In which you need put here
	SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	label.setText(sdf.format(date.getTime()));
    }

    public void onPickButtonClick() {
	// Construct an intent for the place picker
	try {
	    PlacePicker.IntentBuilder intentBuilder =
		new PlacePicker.IntentBuilder();
	    Intent intent = intentBuilder.build(this);
	    // Start the intent by requesting a result,
	    // identified by a request code.
	    startActivityForResult(intent, REQUEST_PLACE_PICKER);

	} catch (GooglePlayServicesRepairableException e) {
	    // ...
	} catch (GooglePlayServicesNotAvailableException e) {
	    // ...
	}
    }

    @Override
    protected void onActivityResult(int requestCode,
				    int resultCode, Intent data) {
	if (requestCode == REQUEST_PLACE_PICKER
	    && resultCode == Activity.RESULT_OK) {
	    // The user has selected a place. Extract the name and address.
	    final Place place = PlacePicker.getPlace(data, this);
	    final CharSequence name = place.getName();
	    final CharSequence address = place.getAddress();
	    locationLatLng = place.getLatLng();
	    String attributions = PlacePicker.getAttributions(data);
	    if (attributions == null) {
		attributions = "";
	    }
	    if (place.getPlaceTypes().get(0) == 0) {
		locationField.setText(address);
	    }
	    else {
		locationField.setText(name);
	    }
	} else if (requestCode == REQUEST_ADD_USERS
	    && resultCode == Activity.RESULT_OK) {


    	} else {
	    super.onActivityResult(requestCode, resultCode, data);
	}
    }
}