package com.example.valentino.rendezvous.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.valentino.rendezvous.R;

public class FilterEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_filter_event);

	Toolbar toolbar;
	toolbar = (Toolbar) findViewById(R.id.topToolBar);
	toolbar.setTitle("Filter");
	toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
	setSupportActionBar(toolbar);

    }

}
