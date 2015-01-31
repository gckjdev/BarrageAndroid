package com.orange.barrage.android.feed.activity;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.squareup.picasso.Picasso;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Created by Rollin on 2015/1/31.
 */
public class FeedCreateActivity extends RoboFragmentActivity {

    @InjectView(R.id.picture_image_view)
    private ImageView mFeedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_create);
        initComponents();
    }

    private void initComponents() {
        String picturePath = getIntent().getStringExtra("path");
        mFeedImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }
}
