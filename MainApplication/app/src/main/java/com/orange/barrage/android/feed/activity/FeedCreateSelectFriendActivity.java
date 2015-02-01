package com.orange.barrage.android.feed.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Created by Rollin on 2015/1/31.
 */
public class FeedCreateSelectFriendActivity extends RoboFragmentActivity {

    @InjectView(R.id.ok_button)
    private Button mOKButton;

    @InjectView(R.id.cancel_button)
    private Button mCancelButton;

    @Inject
    FeedMission mFeedMission;

    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_create_select_friend);
        initComponents();

        initEvents();
    }

    private void initComponents() {
    }

    private void initEvents() {

        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = getIntent().getStringExtra("path");
                final Bitmap image = BitmapFactory.decodeFile(path);
                final String text = getIntent().getStringExtra("subtitle");

                List<UserProtos.PBUser> toUsers = Lists.newArrayList(mUserManager.getUser());
                mFeedMission.createFeed(image, text, toUsers, new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        //FIXME: how to finish all
                        finish();
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        //FIXME: how to finish all
                        finish();
                    }
                });

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
