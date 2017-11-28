package com.example.valentino.rendezvous.listeners;

import com.example.valentino.rendezvous.models.Event;

import java.util.List;

/**
 * Created by Valentino on 11/27/17.
 */

public interface EventListener {
    public void onSuccess(List<Event> events);
}