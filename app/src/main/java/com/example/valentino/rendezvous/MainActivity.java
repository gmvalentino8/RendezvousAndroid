package com.example.valentino.rendezvous;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.valentino.rendezvous.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference myRef = database.getReference("message");
	myRef.setValue("Hello, World!");

	if (findViewById(R.id.content) != null) {
	    if (savedInstanceState != null) {
		return;
	    }
	    HomeFragment homeFragment = new HomeFragment();
	    getSupportFragmentManager().beginTransaction()
		.add(R.id.content, homeFragment).commit();
	}
	setupBottomNavigation();
    }

    private void setupBottomNavigation() {
	Log.d(TAG, "Setting up Bottom Navigation");
	final BottomNavigationViewEx navBar = findViewById(R.id.bottomNavigationBar);
	navBar.enableAnimation(false);
	navBar.enableShiftingMode(false);
	navBar.enableItemShiftingMode(false);
	navBar.setTextVisibility(true);
	navBar.setOnNavigationItemSelectedListener(
	    new BottomNavigationView.OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		    int itemId = item.getItemId();
		    switch (itemId) {
			case R.id.ic_home:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new HomeFragment()).commit();
			    break;
			case R.id.ic_events:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new EventsFragment()).commit();
			    break;
			case R.id.ic_friends:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new FriendsFragment()).commit();
			    break;
			case R.id.ic_profile:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new ProfileFragment()).commit();
			    break;
		    }

		    return true;
		}
	    });
    }
}
