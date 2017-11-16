package com.example.valentino.rendezvous.models;

/**
 * Created by Valentino on 11/16/17.
 */

public class User {

    public String first_name;
    public String last_name;
    public String email;
    public String id;

    public User() {
	// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String first_name, String last_name, String id, String email) {
	this.first_name = first_name;
	this.last_name = last_name;
	this.id = id;
	this.email = email;
    }

}