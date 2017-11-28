package com.example.valentino.rendezvous.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.Group;

/**
 * Created by adityaduri on 11/28/17.
 */

public class GroupsViewHolder extends RecyclerView.ViewHolder {
    private TextView groupNameField;
    private ImageView groupProfileImage;

    public GroupsViewHolder(View itemView) {
        super(itemView);
        groupNameField = (TextView) itemView.findViewById(R.id.groupNameLabel);
        groupProfileImage = (ImageView) itemView.findViewById(R.id.groupProfileImageView);
    }

    public void bindData(final Group viewModel) {
        groupNameField.setText(viewModel.getName());
        groupProfileImage.setImageResource(R.drawable.facebook_icon);
    }

}
