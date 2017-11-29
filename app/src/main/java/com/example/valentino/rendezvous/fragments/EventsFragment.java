package com.example.valentino.rendezvous.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.activities.CreateEventActivity;
import com.example.valentino.rendezvous.activities.FilterEventActivity;
import com.example.valentino.rendezvous.adapters.EventsAdapter;
import com.example.valentino.rendezvous.listeners.EventListener;
import com.example.valentino.rendezvous.models.Event;
import com.example.valentino.rendezvous.dao.EventDAO;
import com.example.valentino.rendezvous.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements View.OnClickListener, EventsAdapter.OnItemClickListener {

    private static final String TAG = EventsFragment.class.getSimpleName();
    public static final String EVENT_TYPE_KEY = "EventTypeKey";


    List<Event> eventsList = new ArrayList<>();
    EventsAdapter adapter;
    RecyclerView recyclerView;
    private Paint p = new Paint();


    public EventsFragment() {
	// Required empty public constructor
    }

    public static EventsFragment newInstance(Bundle args) {
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			     Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_events, container, false);
	view.findViewById(R.id.filter).setOnClickListener(this);
	view.findViewById(R.id.fab).setOnClickListener(this);

	eventsList = new ArrayList<>();
	adapter = new EventsAdapter(eventsList, this);
	recyclerView = (RecyclerView) view.findViewById(R.id.eventsRecyclerView);
	LinearLayoutManager llm = new LinearLayoutManager(getContext());

	String eventsFilter = "None";
	if (getArguments() != null) {
	    eventsFilter = getArguments().getString(EVENT_TYPE_KEY);
	    if (eventsFilter.equals("Going")) {
		view.findViewById(R.id.fab).setVisibility(View.GONE);
		initSwipe(ItemTouchHelper.LEFT);
	    } else if (eventsFilter.equals("Invited")) {
		view.findViewById(R.id.fab).setVisibility(View.GONE);
		initSwipe(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
	    }
	    EventDAO.getFilteredEvents(eventsFilter, new EventListener() {
		@Override
		public void onSuccess(List<Event> events) {
		    adapter.updateEventsList(events);
		}
	    });
	}
	else {
	    EventDAO.getEvents(new EventListener() {
		@Override
		public void onSuccess(List<Event> events) {
		    adapter.updateEventsList(events);
		}
	    });
	    initSwipe(ItemTouchHelper.RIGHT);
	}

	llm.setOrientation(LinearLayoutManager.VERTICAL);
	recyclerView.setAdapter(adapter);
	recyclerView.setLayoutManager(llm);
	recyclerView.setItemAnimator(new DefaultItemAnimator());
	return view;
    }

    @Override
    public void onClick(View view) {
	switch (view.getId()) {
	    case R.id.fab:
		Intent intent = new Intent(getActivity(), CreateEventActivity.class);
		startActivityForResult(intent, 100);
		break;
	    case R.id.filter:
		Intent intent2 = new Intent(getActivity(), FilterEventActivity.class);
		startActivityForResult(intent2, 200);
	}
    }

    private void initSwipe(int swipeDirs){
	ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, swipeDirs) {

	    @Override
	    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		return false;
	    }

	    @Override
	    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		int position = viewHolder.getAdapterPosition();

		if (direction == ItemTouchHelper.LEFT){
		    EventDAO.setEventDecline(adapter.getItem(position).getId());
		    adapter.removeItem(position);
		} else {
		    EventDAO.setEventGoing(adapter.getItem(position).getId());
		    adapter.removeItem(position);
		}
	    }

	    @Override
	    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

		Bitmap icon;
		if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

		    View itemView = viewHolder.itemView;
		    float height = (float) itemView.getBottom() - (float) itemView.getTop();
		    float width = height / 3;

		    if(dX > 0){
			p.setColor(Color.parseColor("#388E3C"));
			RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
			c.drawRect(background,p);
			icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_events);
			RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
			c.drawBitmap(icon,null,icon_dest,p);
		    } else {
			p.setColor(Color.parseColor("#D32F2F"));
			RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
			c.drawRect(background,p);
			icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_friends);
			RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width , (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float)itemView.getBottom() - width);
			c.drawBitmap(icon,null,icon_dest,p);
		    }
		}
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
	    }
	};
	ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
	itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(View view, Event event) {
	FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	EventDetailsFragment detailsFragment = new EventDetailsFragment();
	Bundle b = new Bundle();
	b.putSerializable("Event", event);
	detailsFragment.setArguments(b);
	ft.replace(R.id.content, detailsFragment);
	ft.addToBackStack("eventDetails");
	ft.commit();
    }
}

