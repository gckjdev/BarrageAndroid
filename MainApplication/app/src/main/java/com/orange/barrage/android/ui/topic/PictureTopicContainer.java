package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.widget.LinearLayout;

import com.orange.barrage.android.ui.topic.model.PictureTopicItem;


/**
 * Created by Rollin on 2014/11/30.
 */
public class PictureTopicContainer extends LinearLayout{

    private PictureTopicTopWidget mTopWidget;
    private PictureTopicMainWidget mMainWidget;
    private PictureTopicBottomWidget mBottomWidget;

    public PictureTopicContainer(Context context){
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        //init components
        initComponents(context);
    }

    private void initComponents(Context context) {
        mTopWidget = new PictureTopicTopWidget(context);
        mMainWidget = new PictureTopicMainWidget(context);
        mBottomWidget = new PictureTopicBottomWidget(context);

        this.addView(mTopWidget);
        this.addView(mMainWidget);
        this.addView(mBottomWidget);
    }

    public PictureTopicMainWidget getMainWidget() {
        return mMainWidget;
    }

    public PictureTopicTopWidget getTopWidget() {
        return mTopWidget;
    }

    public PictureTopicBottomWidget getBottomWidget() {
        return mBottomWidget;
    }

}
