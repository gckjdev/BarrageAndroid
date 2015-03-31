package com.orange.barrage.android.feed.activity;

import android.os.Bundle;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.TimelineItemView;
import com.orange.barrage.android.ui.topic.model.FeedModel;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

/**
 * Created by Administrator on 2015/3/30.
 */
public class FeedNewMessageBarrageActivity extends BarrageCommonActivity{


    protected  TimelineItemView mTimelineItemView = null;

    public static String mKey = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_new_message_barrage , R.string.newmessage,-1);
        initView();
    }

    public void initView(){

        mTimelineItemView = (TimelineItemView) findViewById(R.id.timelineitemView);
        FeedModel feedModel = (FeedModel) getIntentSerializable(mKey);
        if(feedModel != null)
            mTimelineItemView.setModel(feedModel);

    }


}
