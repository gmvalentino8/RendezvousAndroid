package com.example.valentino.rendezvous.models;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * Created by Valentino on 11/16/17.
 */

public class Event {
    public enum Status {
	Going, Hosting, Invited
    }

    String name;
    String description;
    String location;
    double latitude;
    double longitude;
    boolean privacy;
    Date startDate;
    Date endDate;
    int maxGoing;
    Map<String, Object> users;

    public Event() {

    }

    public Event(String name, String description, String location, double latitude,
		 double longitude, boolean privacy, Date startDate, Date endDate,
		 int maxGoing, Map<String, Object> users) {
	this.name = name;
	this.description = description;
	this.location = location;
	this.latitude = latitude;
	this.longitude = longitude;
	this.privacy = privacy;
	this.startDate = startDate;
	this.endDate = endDate;
	this.maxGoing = maxGoing;
	this.users = users;
    }


    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public double getLatitude() {
	return latitude;
    }

    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    public double getLongitude() {
	return longitude;
    }

    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

    public boolean isPrivacy() {
	return privacy;
    }

    public void setPrivacy(boolean privacy) {
	this.privacy = privacy;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public int getMaxGoing() {
	return maxGoing;
    }

    public void setMaxGoing(int maxGoing) {
	this.maxGoing = maxGoing;
    }

    public Map<String, Object> getUsers() {
	return users;
    }

    public void setUsers(Map<String, Object> users) {
	this.users = users;
    }


}
