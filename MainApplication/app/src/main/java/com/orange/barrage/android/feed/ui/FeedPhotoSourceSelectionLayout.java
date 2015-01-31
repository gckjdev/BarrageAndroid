package com.orange.barrage.android.feed.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.event.ActionImageCaptureEvent;
import com.orange.barrage.android.event.ActionPickEvent;
import com.orange.barrage.android.util.ContextManager;

import java.io.File;

import de.greenrobot.event.EventBus;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Rollin on 2015/1/31.
 */
public class FeedPhotoSourceSelectionLayout extends LinearLayout{

    private View mTakePhotoView;
    private View mPictureSelectView;
    private Context mContext;

    public FeedPhotoSourceSelectionLayout(Context context) {
        this(context, null);
    }

    public FeedPhotoSourceSelectionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initComponents(context);
        initEvents();
    }

    private void initComponents(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(
                R.layout.view_feed_photo_source_selection,this);
        mTakePhotoView = findViewById(R.id.take_photo_layout);
        mPictureSelectView = findViewById(R.id.picture_select_layout);
    }

    public void initEvents(){
        mTakePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionImageCaptureEvent event = new ActionImageCaptureEvent();
                EventBus.getDefault().post(event);
            }
        });

        mPictureSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionPickEvent event = new ActionPickEvent();
                event.setType("image/*");
                EventBus.getDefault().post(event);
            }
        });
    }

}
