package com.example.valentino.rendezvous.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

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
    double latitute;
    double longitude;
    boolean privacy;
    Date startDate;
    Date endDate;
    int maxGoing;
    Dictionary<Integer, String> users;

    public Event(String name, String description, String location, double latitude,
		 double longitude, boolean privacy, Date startDate, Date endDate,
		 int maxGoing, Dictionary<Integer, String> users) {
	this.name = name;
	this.description = description;
	this.location = location;
	this.latitute = latitude;
	this.longitude = longitude;
	this.privacy = privacy;
	this.startDate = startDate;
	this.endDate = endDate;
	this.maxGoing = maxGoing;
	this.users = users;
    }

}
