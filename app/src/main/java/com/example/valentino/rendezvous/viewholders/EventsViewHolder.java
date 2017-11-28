package com.example.valentino.rendezvous.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by adityaduri on 11/28/17.
 */

public class EventsViewHolder extends RecyclerView.ViewHolder {
    private TextView eventNameField;
    private TextView eventLocationField;
    private TextView eventTimeField;

    public EventsViewHolder(View itemView) {
        super(itemView);
        eventNameField = (TextView) itemView.findViewById(R.id.eventname);
        eventLocationField = (TextView) itemView.findViewById(R.id.eventlocation);
        eventTimeField = (TextView) itemView.findViewById(R.id.eventtime);
    }

    public void bindData(final Event viewModel) {
        eventNameField.setText(viewModel.getName());
        eventLocationField.setText(viewModel.getLocation());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        eventTimeField.setText(df.format(viewModel.getStartDate()) + " – " + df.format(viewModel.getEndDate()));
    }
}
