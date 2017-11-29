package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentino on 11/29/17.
 */

public class AddUserAdapter extends RecyclerView.Adapter {
    private List<User> models = new ArrayList<>();

    public AddUserAdapter(final List<User> viewModels) {
	if (viewModels != null) {
	    this.models.addAll(viewModels);
	}
    }

    public void updateAddUserList(List<User> newlist) {
	models.clear();
	models.addAll(newlist);
	this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
	AddUserViewHolder holder = new AddUserViewHolder(view);
	return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
	((AddUserViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
	return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
	return R.layout.item_add_user;
    }

    static class AddUserViewHolder extends RecyclerView.ViewHolder {
	private ImageView friendProfileImage;

	public AddUserViewHolder(View itemView) {
	    super(itemView);
	    friendProfileImage = (ImageView) itemView.findViewById(R.id.friendProfileImageView);
	}

	public void bindData(final User viewModel) {
	    Glide.with(itemView)
		.load(viewModel.picture)
		.into(friendProfileImage);
	}

    }
}
