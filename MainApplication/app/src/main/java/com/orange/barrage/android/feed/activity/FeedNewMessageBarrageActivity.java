package com.orange.barrage.android.feed.activity;

import android.os.Bundle;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.TimelineItemView;
import com.orange.barrage.android.ui.topic.model.FeedModel;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;

import roboguice.inject.InjectView;

/**
 * Created by Administrator on 2015/3/30.
 */
public class FeedNewMessageBarrageActivity extends BarrageCommonActivity{

    @InjectView(R.id.timelineitemView)
    protected  TimelineItemView mTimelineItemView = null;

    public static String mKey = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_new_message_barrage , R.string.newmessage,-1);
        initView();
    }

    public void initView(){

        FeedModel feedModel = (FeedModel) getIntentSerializable(mKey);
        if(feedModel != null)
            mTimelineItemView.setModel(feedModel , LayoutDrawIconBackground.LAYOUT_DRAWBAKGROUND);

    }


    @Override
    protected void onStop() {
        super.onStop();
        mTimelineItemView.stopPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



}
