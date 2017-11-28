package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Group;
import com.example.valentino.rendezvous.viewholders.GroupsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adityaduri on 11/28/17.
 */

public class GroupsAdapter extends RecyclerView.Adapter {
    private List<Group> models = new ArrayList<>();

    public GroupsAdapter(final List<Group> viewModels) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
        }
    }

    public void updateGroupsList(List<Group> newlist) {
        models.clear();
        models.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new GroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupsViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_group;
    }
}
