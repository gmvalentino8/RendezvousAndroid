package com.example.valentino.rendezvous.activities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.valentino.rendezvous.fragments.EventsFragment;
import com.example.valentino.rendezvous.fragments.FriendsFragment;
import com.example.valentino.rendezvous.fragments.HomeFragment;
import com.example.valentino.rendezvous.fragments.ProfileFragment;
import com.example.valentino.rendezvous.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	if (findViewById(R.id.content) != null) {
	    if (savedInstanceState != null) {
		return;
	    }
	    EventsFragment initialFragment = new EventsFragment();
	    getSupportFragmentManager().beginTransaction()
		.add(R.id.content, initialFragment).commit();
	}


	toolbar = (Toolbar) findViewById(R.id.topToolBar);
	setSupportActionBar(toolbar);
	toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

	setupBottomNavigation();
    }

    @Override
    protected void onResume() {
	super.onResume();
	setupBottomNavigation();
    }

    private void setupBottomNavigation() {
	Log.d(TAG, "Setting up Bottom Navigation");
	final BottomNavigationViewEx navBar = findViewById(R.id.bottomNavigationBar);
	navBar.enableAnimation(false);
	navBar.enableShiftingMode(false);
	navBar.enableItemShiftingMode(false);
	navBar.setTextVisibility(true);
	navBar.setSelectedItemId(R.id.ic_events);
	navBar.setOnNavigationItemSelectedListener(
	    new BottomNavigationView.OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		    int itemId = item.getItemId();
		    switch (itemId) {
			case R.id.ic_home:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new HomeFragment())
				.addToBackStack("Home").commit();
			    break;
			case R.id.ic_events:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new EventsFragment())
				.addToBackStack("Events").commit();
			    break;
			case R.id.ic_friends:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new FriendsFragment())
				.addToBackStack("Friends").commit();
			    break;
			case R.id.ic_profile:
			    getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new ProfileFragment())
				.addToBackStack("Profile").commit();
			    break;
		    }
		    return true;
		}
	    });
    }
}
