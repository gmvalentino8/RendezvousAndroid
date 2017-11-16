package com.example.valentino.rendezvous;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button logOutButton;

    public ProfileFragment() {
	// Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
	ProfileFragment fragment = new ProfileFragment();
	return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	// Inflate the layout for this fragment
	View view = inflater.inflate(R.layout.fragment_profile, container, false);
	logOutButton = (Button) view.findViewById(R.id.signOutButton);
	logOutButton.setOnClickListener(this);
	return view;
    }

    @Override
    public void onClick(View view) {
	FirebaseAuth.getInstance().signOut();
	LoginManager.getInstance().logOut();
	AccessToken.setCurrentAccessToken(null);
	goToLoginActivity();
    }

    private void goToLoginActivity() {
	Intent intent = new Intent(getActivity(), LoginActivity.class);
	startActivity(intent);
    }
}
