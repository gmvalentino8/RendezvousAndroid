package com.example.valentino.rendezvous.fragments;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.LoginActivity;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView logOutButton;
    TextView EditButton;
    TextView SaveButton;
    TextView firstname;
    TextView lastname;
    EditText changefirst;
    EditText changelast;
    TextView email;
    EditText changemail;
    private DatabaseReference mDatabase;

    public ProfileFragment() {
        // Required empty public constructor
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
        EditButton = (TextView) view.findViewById(R.id.editProfileButton);
        SaveButton = (TextView) view.findViewById(R.id.saveProfileButton);
        logOutButton = (TextView) view.findViewById(R.id.signOutButton);
        firstname= (TextView) view.findViewById(R.id.firstname);
        lastname = (TextView) view.findViewById(R.id.lastname);
        email = (TextView) view.findViewById(R.id.currentmail);
        changemail = (EditText) view.findViewById(R.id.changedmail);
        changefirst = (EditText) view.findViewById(R.id.changefirstname);
        changelast = (EditText) view.findViewById(R.id.changelastname);
        logOutButton.setOnClickListener(this);
        EditButton.setOnClickListener(this);
        SaveButton.setOnClickListener(this);
        firstname.setVisibility(View.VISIBLE);
        lastname.setVisibility(View.VISIBLE);
        EditButton.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        changemail.setVisibility(View.GONE);
        SaveButton.setVisibility(View.GONE);
        changefirst.setVisibility(View.GONE);
        changelast.setVisibility(View.GONE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("firstName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                changefirst.setText(snapshot.getValue().toString());
                firstname.setText(snapshot.getValue().toString());//prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("lastName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                changelast.setText(snapshot.getValue().toString());
                lastname.setText(snapshot.getValue().toString());//prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                changemail.setText(snapshot.getValue().toString());
                email.setText(snapshot.getValue().toString());//prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signOutButton:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                goToLoginActivity();
                break;
            case R.id.editProfileButton:

                firstname.setVisibility(View.GONE);
                lastname.setVisibility(View.GONE);
                EditButton.setVisibility(View.GONE);
                SaveButton.setVisibility(View.VISIBLE);
                changefirst.setVisibility(View.VISIBLE);
                changelast.setVisibility(View.VISIBLE);
                email.setVisibility(View.GONE);
                changemail.setVisibility(View.VISIBLE);

                break;

            case R.id.saveProfileButton:


                String changedfirst = changefirst.getText().toString();
                String changedlast = changelast.getText().toString();
                String changedmail = changemail.getText().toString();

                firstname.setText(changedfirst);
                lastname.setText(changedlast);
                email.setText(changedmail);

                mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("firstName").setValue(changedfirst);
                mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("lastName").setValue(changedlast);
                mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("email").setValue(changedmail);



                Toast.makeText(getContext(), "saved profile", Toast.LENGTH_SHORT).show();

                firstname.setVisibility(View.VISIBLE);
                lastname.setVisibility(View.VISIBLE);
                EditButton.setVisibility(View.VISIBLE);
                SaveButton.setVisibility(View.GONE);
                changefirst.setVisibility(View.GONE);
                changelast.setVisibility(View.GONE);
                email.setVisibility(View.VISIBLE);
                changemail.setVisibility(View.GONE);

                break;
        }

    }

    private void goToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
