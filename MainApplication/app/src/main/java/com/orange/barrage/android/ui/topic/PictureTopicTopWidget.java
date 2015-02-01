package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicTopWidget extends LinearLayout{


    private PictureTopicContainer mContainer;

    public PictureTopicTopWidget(Context context, PictureTopicContainer container){
        super(context);
        mContainer = container;

        TextView view = new TextView(context);
        view.setText("Top Container");
        this.addView(view);
    }
}
