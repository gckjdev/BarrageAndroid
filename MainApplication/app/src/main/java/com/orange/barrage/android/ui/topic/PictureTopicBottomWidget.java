package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicBottomWidget extends LinearLayout{

    public PictureTopicBottomWidget(Context context){
        super(context);

        TextView view = new TextView(context);
        view.setText("Bottom Container");
        this.addView(view);
    }
}
