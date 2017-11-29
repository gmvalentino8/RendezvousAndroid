package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Valentino on 11/27/17.
 */

public class FriendsAdapter extends RecyclerView.Adapter {
    public interface OnItemClickListener {
        void onItemClick(View view, User user);
    };

    private final OnItemClickListener listener;
    private List<User> models = new ArrayList<>();
    private Set<User> added = new HashSet<>();
    private boolean addFriends;

    public FriendsAdapter(final List<User> viewModels, final Set<User> addedFriends, OnItemClickListener listener, boolean addFriends) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
	}
        this.addFriends = addFriends;
        if (addFriends) {
            this.added.addAll(addedFriends);
        }
        this.listener = listener;
    }

    public void updateFriendsList(List<User> newlist) {
        models.clear();
        models.addAll(newlist);
        this.notifyDataSetChanged();
    }

    public void hideAddIndicator(RecyclerView.ViewHolder holder) {
        ((FriendsViewHolder) holder).itemView.findViewById(R.id.addIndicatorImage).setVisibility(View.INVISIBLE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        FriendsViewHolder holder = new FriendsViewHolder(view);
        if (!addFriends) {
            hideAddIndicator(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
	    ((FriendsViewHolder) holder).bindData(models.get(position), added, listener);
    }

    @Override
    public int getItemCount() {
	return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_friend;
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {
        private TextView friendNameField;
        private ImageView friendProfileImage;
        private ImageView addIndicatorImage;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            friendNameField = (TextView) itemView.findViewById(R.id.friendNameLabel);
            friendProfileImage = (ImageView) itemView.findViewById(R.id.friendProfileImageView);
            addIndicatorImage = (ImageView) itemView.findViewById(R.id.addIndicatorImage);
        }

        public void bindData(final User viewModel, final Set<User> added, final OnItemClickListener listener) {
            friendNameField.setText(viewModel.getFirstName() + " " + viewModel.getLastName());
            Glide.with(itemView)
                .load(viewModel.picture)
                .into(friendProfileImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(itemView, viewModel);
                }
            });
            if (added.contains(viewModel)) {
                addIndicatorImage.setImageResource(R.drawable.ic_friends);
            }
        }

    }

}
