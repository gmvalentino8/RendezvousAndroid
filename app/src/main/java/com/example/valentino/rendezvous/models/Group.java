package com.example.valentino.rendezvous.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Valentino on 11/16/17.
 */

public class Group implements Serializable {
    String id;
    String name;
    String picture;
    List<String> users;

    public Group() {

    }

    public Group(String name, String picture, List<String> users) {
        this.name = name;
        this.picture = picture;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
