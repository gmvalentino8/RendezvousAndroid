package com.example.valentino.rendezvous.listeners;

import com.example.valentino.rendezvous.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Valentino on 11/27/17.
 */

public interface UserListener {
    public void onSuccess(List<User> users);
}