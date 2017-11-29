package com.example.valentino.rendezvous.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adityaduri on 11/28/17.
 */

public class GroupsAdapter extends RecyclerView.Adapter {
    public interface OnItemClickListener {
        void onItemClick(View view, Group group);
    };
    private final OnItemClickListener listener;
    private List<Group> models = new ArrayList<>();

    public GroupsAdapter(final List<Group> viewModels, OnItemClickListener listener) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
        }
        this.listener = listener;
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
        ((GroupsViewHolder) holder).bindData(models.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_group;
    }

    public static class GroupsViewHolder extends RecyclerView.ViewHolder {
        private TextView groupNameField;
        private ImageView groupProfileImage;

        public GroupsViewHolder(View itemView) {
            super(itemView);
            groupNameField = (TextView) itemView.findViewById(R.id.groupNameLabel);
            groupProfileImage = (ImageView) itemView.findViewById(R.id.groupProfileImageView);
        }

        public void bindData(final Group viewModel, final OnItemClickListener listener) {
            groupNameField.setText(viewModel.getName());
            groupProfileImage.setImageResource(R.drawable.facebook_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(itemView, viewModel);
                }
            });
        }

    }
}
