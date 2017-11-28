package com.example.valentino.rendezvous.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.valentino.rendezvous.R;
import com.example.valentino.rendezvous.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.facebook.share.internal.ShareConstants.IMAGE_URL;

/**
 * Created by Valentino on 11/27/17.
 */

public class FriendsViewHolder extends RecyclerView.ViewHolder {
    private TextView friendNameField;
    private ImageView friendProfileImage;

    public FriendsViewHolder(View itemView) {
	super(itemView);
	friendNameField = (TextView) itemView.findViewById(R.id.friendNameLabel);
	friendProfileImage = (ImageView) itemView.findViewById(R.id.friendProfileImageView);
    }

    public void bindData(final User viewModel) {
	friendNameField.setText(viewModel.getFirstName() + " " + viewModel.getLastName());
	Glide.with(itemView)
	    .load(viewModel.picture)
	    .into(friendProfileImage);
    }


}
