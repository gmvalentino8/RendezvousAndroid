package com.example.valentino.rendezvous.listeners;

import com.example.valentino.rendezvous.models.Group;

import java.util.List;

/**
 * Created by adityaduri on 11/28/17.
 */

public interface GroupListener {
    public void onSuccess(List<Group> groups);
}