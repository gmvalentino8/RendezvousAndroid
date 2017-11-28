package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.viewholders.EventsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adityaduri on 11/28/17.
 */

public class EventsAdapter extends RecyclerView.Adapter {
    private List<Event> models = new ArrayList<>();

    public EventsAdapter(final List<Event> viewModels) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
        }
    }

    public void updateEventsList(List<Event> newlist) {
        models.clear();
        models.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EventsViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_event;
    }
}
