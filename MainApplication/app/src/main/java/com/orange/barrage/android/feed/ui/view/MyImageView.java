package com.orange.barrage.android.feed.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by youjiannuo on 2015/3/6.
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setMaxHeight(getWidth());

    }
}
