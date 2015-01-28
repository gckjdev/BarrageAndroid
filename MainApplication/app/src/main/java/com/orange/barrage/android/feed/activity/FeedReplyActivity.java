package com.orange.barrage.android.feed.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.ui.component.DraggableOnTouchListener;
import com.orange.barrage.android.ui.topic.FeedActionWidget;
import com.orange.barrage.android.ui.topic.PictureTopicMainWidget;
import com.orange.barrage.android.ui.topic.PictureTopicMode;
import com.orange.barrage.android.ui.topic.data.dummy.PictureTopicDummyDataGen;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.util.ContextManager;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Created by Rollin on 2015/1/24.
 */
public class FeedReplyActivity extends RoboFragmentActivity {

    @InjectView(R.id.picture_topic_main_widget)
    PictureTopicMainWidget mMainWidget;

    @InjectView(R.id.reply_feed_btn_send)
    Button mSendButton;

    @InjectView(R.id.reply_feed_btn_cancel)
    Button mCancelButton;

    @InjectView(R.id.picture_topic_main_widget_bg)
    FrameLayout mMainWidgetBackGround;

    private FeedActionWidget mFeedActionWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_reply);
        initComponents();
    }

    public void initComponents(){
        Context context = ContextManager.getContext();
        mFeedActionWidget = new FeedActionWidget(context);
        mFeedActionWidget.setOnTouchListener(new DraggableOnTouchListener());
        mFeedActionWidget.setX(100);
        mFeedActionWidget.setY(200);

        PictureTopicModel model = (PictureTopicModel)getIntent().getSerializableExtra("model");
        mMainWidget.setModel(model);
        mMainWidget.hideAllBarrageActions();
        mMainWidget.setMode(PictureTopicMode.COMMENT);

        //FIXME :get curent user
        mFeedActionWidget.setAvadar(PictureTopicDummyDataGen.getAvatar());
        mFeedActionWidget.setContent("Please input content");
        this.mMainWidgetBackGround.addView(mFeedActionWidget);
    }
}
