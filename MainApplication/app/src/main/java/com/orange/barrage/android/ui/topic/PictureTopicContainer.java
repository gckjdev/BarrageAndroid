package com.orange.barrage.android.ui.topic;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import com.orange.barrage.android.ui.topic.model.PictureTopicModel;


/**
 * Created by Rollin on 2014/11/30.
 */
@Deprecated
public class PictureTopicContainer extends LinearLayout{

    private PictureTopicModel mModel;
    private PictureTopicTopWidget mTopWidget;
    private FeedMainWidget mMainWidget;
    private PictureTopicBottomWidget mBottomWidget;
    private Activity mActivity;

    public PictureTopicContainer(Context context){
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        mModel = new PictureTopicModel();
        //init components
        initComponents(context);
    }

    private void initComponents(Context context) {
        mTopWidget = new PictureTopicTopWidget(context, this);
        //mMainWidget = new PictureTopicMainWidget(context, null, this);
        mBottomWidget = new PictureTopicBottomWidget(context, this);

        this.addView(mTopWidget);
        this.addView(mMainWidget);
        this.addView(mBottomWidget);
    }

    public FeedMainWidget getMainWidget() {
        return mMainWidget;
    }

    public PictureTopicTopWidget getTopWidget() {
        return mTopWidget;
    }

    public PictureTopicBottomWidget getBottomWidget() {
        return mBottomWidget;
    }

    public void setModel(PictureTopicModel model , Activity activity) {
        this.mActivity = activity;
        mModel = model;
        mMainWidget.setModel(mModel);
    }

    public PictureTopicModel getModel() {
        return mModel;
    }
}
