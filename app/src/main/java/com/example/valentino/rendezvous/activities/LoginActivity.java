package com.example.valentino.rendezvous.activities;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.dao.UserDAO;
import com.example.valentino.rendezvous.models.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private LoginManager loginManager;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private Button fbLoginButton;

    @Override
    public void onClick(View view) {
	loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
    }

    @Override
    public void onStart() {
	super.onStart();
	// Check if user is signed in (non-null) and update UI accordingly.
	if (AccessToken.getCurrentAccessToken() != null && firebaseAuth.getCurrentUser() != null) {
	    goToMainActivity();
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (Button) findViewById(R.id.fb_login_button);
	fbLoginButton.setOnClickListener(this);
	firebaseAuth = FirebaseAuth.getInstance();
	loginManager = LoginManager.getInstance();
        loginManager.setLoginBehavior(LoginBehavior.WEB_ONLY);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
	    @Override
	    public void onSuccess(LoginResult loginResult) {
		Log.d(TAG, "Success");
		handleFacebookAccessToken(loginResult.getAccessToken());
	    }

	    @Override
	    public void onCancel() {
		Log.d(TAG, "Cancel");
	    }

	    @Override
	    public void onError(FacebookException e) {
		Log.d(TAG, "Error" + e.getLocalizedMessage());
	    }
	});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void goToMainActivity() {
	Intent intent = new Intent(this, MainActivity.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(intent);
    }

    private void handleFacebookAccessToken(final AccessToken token) {
	Log.d(TAG, "handleFacebookAccessToken:" + token);

	AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
	firebaseAuth.signInWithCredential(credential)
	    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
		@Override
		public void onComplete(@NonNull Task<AuthResult> task) {
		    if (task.isSuccessful()) {
			Log.d(TAG, "signInWithCredential:success");
			UserDAO.setUserData(token);
			UserDAO.setFriendsList();
			goToMainActivity();
		    } else {
			Log.w(TAG, "signInWithCredential:failure", task.getException());
		    }
		}
	    });
    }

}
