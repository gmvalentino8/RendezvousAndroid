package com.example.valentino.rendezvous.models;

import java.io.Serializable;

/**
 * Created by Valentino on 11/16/17.
 */

public class User implements Serializable {

    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String picture;

    public User() {
	// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName, String picture, String email) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.picture = picture;
	this.email = email;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPicture() {
	return picture;
    }

    public void setPicture(String picture) {
	this.picture = picture;
    }

}