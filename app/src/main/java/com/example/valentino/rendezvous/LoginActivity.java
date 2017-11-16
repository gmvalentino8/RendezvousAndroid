package com.example.valentino.rendezvous;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private LoginManager loginManager;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private Button fbLoginButton;

    @Override
    public void onClick(View view) {
	loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    public void onStart() {
	super.onStart();
	// Check if user is signed in (non-null) and update UI accordingly.
	FirebaseUser currentUser = firebaseAuth.getCurrentUser();
	if (currentUser != null) {
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

    private void handleFacebookAccessToken(AccessToken token) {
	Log.d(TAG, "handleFacebookAccessToken:" + token);

	AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
	firebaseAuth.signInWithCredential(credential)
	    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
		@Override
		public void onComplete(@NonNull Task<AuthResult> task) {
		    if (task.isSuccessful()) {
			// Sign in success, update UI with the signed-in user's information
			Log.d(TAG, "signInWithCredential:success");
			FirebaseUser user = firebaseAuth.getCurrentUser();
			goToMainActivity();
		    } else {
			// If sign in fails, display a message to the user.
			Log.w(TAG, "signInWithCredential:failure", task.getException());
		    }
		}
	    });
    }

}
