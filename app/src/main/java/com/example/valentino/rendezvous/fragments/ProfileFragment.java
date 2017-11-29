package com.example.valentino.rendezvous.fragments;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.valentino.rendezvous.dao.UserDAO;
import com.example.valentino.rendezvous.listeners.UserListener;
import com.example.valentino.rendezvous.models.User;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    User user;
    TextView logOutButton;
    EditText nameEditText;
    EditText emailEditText;
    ImageView profileImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        if (user == null) {
            UserDAO.getUserProfile(Profile.getCurrentProfile().getId(), new UserListener() {
                @Override
                public void onSuccess(List<User> users) {
                    user = users.get(0);
                    displayUserInformation(view, user);
                }
            });
        }
        else {
            displayUserInformation(view, user);
            logOutButton.setVisibility(View.GONE);
        }

        return view;
    }

    public void initViews(View view) {
        nameEditText = (EditText) view.findViewById(R.id.nameField);
        nameEditText.setEnabled(false);
        emailEditText = (EditText) view.findViewById(R.id.emailField);
        emailEditText.setEnabled(false);
        logOutButton = (TextView) view.findViewById(R.id.signOutButton);
        profileImageView = (ImageView) view.findViewById(R.id.profileImageView);
        logOutButton.setOnClickListener(this);
    }

    public void displayUserInformation(View view, User user) {
        nameEditText.setText(user.firstName + " " + user.lastName);
        emailEditText.setText(user.email);
        RequestOptions options = new RequestOptions();
        options.override(profileImageView.getWidth(), profileImageView.getHeight());
        Glide
            .with(view)
            .load(user.picture)
            .apply(options)
            .into(profileImageView);
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
//            case R.id.editProfileButton:
//
//                firstname.setVisibility(View.GONE);
//                lastname.setVisibility(View.GONE);
//                EditButton.setVisibility(View.GONE);
//                SaveButton.setVisibility(View.VISIBLE);
//                changefirst.setVisibility(View.VISIBLE);
//                changelast.setVisibility(View.VISIBLE);
//                email.setVisibility(View.GONE);
//                changemail.setVisibility(View.VISIBLE);
//
//                break;
//
//            case R.id.saveProfileButton:
//
//                String changedfirst = changefirst.getText().toString();
//                String changedlast = changelast.getText().toString();
//                String changedmail = changemail.getText().toString();
//
//                firstname.setText(changedfirst);
//                lastname.setText(changedlast);
//                email.setText(changedmail);
//
//                mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("firstName").setValue(changedfirst);
//                mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("lastName").setValue(changedlast);
//                mDatabase.child("android_users").child(Profile.getCurrentProfile().getId()).child("email").setValue(changedmail);
//
//                Toast.makeText(getContext(), "saved profile", Toast.LENGTH_SHORT).show();
//
//                firstname.setVisibility(View.VISIBLE);
//                lastname.setVisibility(View.VISIBLE);
//                EditButton.setVisibility(View.VISIBLE);
//                SaveButton.setVisibility(View.GONE);
//                changefirst.setVisibility(View.GONE);
//                changelast.setVisibility(View.GONE);
//                email.setVisibility(View.VISIBLE);
//                changemail.setVisibility(View.GONE);
//
//                break;
        }

    }

    private void goToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
