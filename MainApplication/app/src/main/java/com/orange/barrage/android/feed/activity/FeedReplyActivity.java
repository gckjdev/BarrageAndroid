package com.orange.barrage.android.feed.activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.ui.component.DraggableOnTouchListener;
import com.orange.barrage.android.ui.topic.FeedActionWidget;
import com.orange.barrage.android.ui.topic.PictureTopicMainWidget;
import com.orange.barrage.android.ui.topic.PictureTopicMode;
import com.orange.barrage.android.ui.topic.data.dummy.PictureTopicDummyDataGen;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.ToastUtil;
import com.orange.protocol.message.BarrageProtos;

import java.util.List;

import javax.inject.Inject;

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

    @Inject
    private FeedMission mFeedMission;

    private FeedActionWidget mFeedActionWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_reply);
        initComponents();
    }

    public void initComponents() {
        Context context = ContextManager.getContext();
        mFeedActionWidget = new FeedActionWidget(context, true);
        mFeedActionWidget.setOnTouchListener(new DraggableOnTouchListener());

        //FIXME: get
        mFeedActionWidget.setX(100);
        mFeedActionWidget.setY(200);

        PictureTopicModel model = (PictureTopicModel) getIntent().getSerializableExtra("model");
        mMainWidget.setModel(model);
        mMainWidget.hideAllBarrageActions();
        mMainWidget.setMode(PictureTopicMode.COMMENT);

        //FIXME :get curent user
        mFeedActionWidget.setAvadar(PictureTopicDummyDataGen.getAvatar());
        mFeedActionWidget.setContent("Please input content");
        this.mMainWidgetBackGround.addView(mFeedActionWidget);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "";
                String text = "";
                float x = 0;
                float y = 0;
                String feedId = "";
                BarrageProtos.PBFeedAction feedAction = BarrageProtos.PBFeedAction.newBuilder().setFeedId(feedId).setAvatar(avatar).setText(text).setPosX(x).setPosY(y).build();
                mFeedMission.replyFeed(feedAction, new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        finish();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        ToastUtil.showToastMessage(ContextManager.getContext(), "", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
