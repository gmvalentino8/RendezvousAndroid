package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.User;
import com.example.valentino.rendezvous.viewholders.FriendsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentino on 11/27/17.
 */

public class FriendsAdapter extends RecyclerView.Adapter {

    private List<User> models = new ArrayList<>();

    public FriendsAdapter(final List<User> viewModels) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
	}
    }

    public void updateFriendsList(List<User> newlist) {
	models.clear();
	models.addAll(newlist);
	this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
	return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
	((FriendsViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
	return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_friend;
    }
}
