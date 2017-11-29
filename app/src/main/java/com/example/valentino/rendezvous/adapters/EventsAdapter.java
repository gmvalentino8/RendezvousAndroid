package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adityaduri on 11/28/17.
 */

public class EventsAdapter extends RecyclerView.Adapter {
    public interface OnItemClickListener {
        void onItemClick(View view, Event event);
    };

    private final OnItemClickListener listener;
    private List<Event> models = new ArrayList<>();

    public EventsAdapter(final List<Event> viewModels, OnItemClickListener listener) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
        }
        this.listener = listener;
    }

    public void updateEventsList(List<Event> newlist) {
        models.clear();
        models.addAll(newlist);
        this.notifyDataSetChanged();
    }

    public Event getItem(int position) {
        return models.get(position);
    }

    public void removeItem(int position) {
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EventsViewHolder) holder).bindData(models.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_event;
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        private TextView eventNameField;
        private TextView eventLocationField;
        private TextView eventTimeField;

        public EventsViewHolder(View itemView) {
            super(itemView);
            eventNameField = (TextView) itemView.findViewById(R.id.eventname);
            eventLocationField = (TextView) itemView.findViewById(R.id.eventlocation);
            eventTimeField = (TextView) itemView.findViewById(R.id.eventtime);
        }

        public void bindData(final Event viewModel, final OnItemClickListener listener) {
            eventNameField.setText(viewModel.getName());
            eventLocationField.setText(viewModel.getLocation());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            eventTimeField.setText(df.format(viewModel.getStartDate()) + " – " + df.format(viewModel.getEndDate()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(itemView, viewModel);
                }
            });
        }
    }
}
