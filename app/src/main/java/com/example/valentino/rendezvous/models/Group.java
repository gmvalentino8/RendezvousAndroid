package com.example.valentino.rendezvous.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Valentino on 11/16/17.
 */

public class Group {
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
}
