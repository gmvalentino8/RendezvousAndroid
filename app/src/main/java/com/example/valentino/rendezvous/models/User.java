package com.example.valentino.rendezvous.models;

/**
 * Created by Valentino on 11/16/17.
 */

public class User {

    public String firstName;
    public String lastName;
    public String email;
    public String facebookID;

    public User() {
	// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName, String facebookID, String email) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.facebookID = facebookID;
	this.email = email;
    }

}