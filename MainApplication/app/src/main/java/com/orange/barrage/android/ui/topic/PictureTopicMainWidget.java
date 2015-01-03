package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.orange.barrage.android.model.PictureTopicItem;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicMainWidget extends LinearLayout {

    private PictureTopicItem mItem;

    FetchableImageView mImage;

    public PictureTopicMainWidget(Context context){
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        TextView view = new TextView(context);
        view.setText("Main Container");
        this.addView(view);

        mImage = new FetchableImageView(context, null);

        this.addView(mImage);
    }

    public void setModel(PictureTopicItem model){
        mItem = model;
        mImage.setImage( model.getPictureUrl());
    }
}
