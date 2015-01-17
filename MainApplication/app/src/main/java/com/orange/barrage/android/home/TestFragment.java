package com.orange.barrage.android.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.ui.topic.PictureTopicMainWidget;
import com.orange.barrage.android.ui.topic.data.dummy.PictureTopicDummyDataGen;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.ToastUtil;
import com.orange.barrage.android.util.persistent.LevelDBTestDAO;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * Created by pipi on 15/1/6.
 */
public class TestFragment extends RoboFragment {

    @Inject
    LevelDBTestDAO mLevelDBTestDAO;

    @Inject
    FeedMission mFeedMission;
    @Inject
    UserManager mUserManager;

    @InjectView(R.id.test_button)
    Button mTestButton;

    @InjectView(R.id.textset)
    TextView mTextView;

    @InjectView(R.id.test_db_button)
    Button mTestDBButton;

    @InjectView(R.id.play_button)
    Button mPlayButton;

    @InjectView(R.id.pause_button)
    Button mPauseButton;

    @InjectView(R.id.resume_button)
    Button mResumeButton;

    @InjectView(R.id.move_to_button)
    Button mMoveToButton;

    @InjectView(R.id.picture_topic_main_widget)
    PictureTopicMainWidget mMainWidget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.test_fragment, container, false);

        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTextView.setText("Hello World");
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedMission.getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        ToastUtil.showToastMessage(ContextManager.getContext(), "handleSuccess timeline", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        ToastUtil.showToastMessage(ContextManager.getContext(), "handleFailure timeline:"+ errorCode, Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainWidget.play();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainWidget.pause();
            }
        });

        mResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainWidget.resume();
            }
        });

        mMoveToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float delta = new Random().nextFloat();
                float progress = ((float)mCurrentCommentSize) * delta;
                mMainWidget.moveTo(progress);
                ToastUtil.showToastMessage(ContextManager.getContext(), "moveTo:"+progress, Toast.LENGTH_SHORT);
            }
        });

        // YOU CAN ADD SOME TEST CODE HERE
        mTestDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLevelDBTestDAO.saveText("id1", "value1");
                String value = mLevelDBTestDAO.getText("id1");
                Toast.makeText(ContextManager.getContext(),value, Toast.LENGTH_SHORT).show();
            }
        });

        initMainWidget();
    }

    private void testSubmitFeed(){
        final Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        final String text = "Hello";
        final List<UserProtos.PBUser> toUsers = new ArrayList<UserProtos.PBUser>();
        toUsers.add(mUserManager.getUser());
        mFeedMission.createFeed(image, text, toUsers, new FeedMissionCallbackInterface() {

            @Override
            public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                ToastUtil.showToastMessage(ContextManager.getContext(), "handleSuccess submit", Toast.LENGTH_SHORT);
            }

            @Override
            public void handleFailure(int errorCode) {
                ToastUtil.showToastMessage(ContextManager.getContext(), "handleFailure submit:" + errorCode, Toast.LENGTH_SHORT);
            }
        });
    }

    private int mCurrentCommentSize = 0;

    private void initMainWidget(){
        String imageURL = PictureTopicDummyDataGen.getImange();
        mMainWidget.setImangeURL(imageURL);
        mMainWidget.setSubtitle("Subtitle Text");

        List<BarrageProtos.PBFeedAction> feedActionList = Lists.newArrayList();

        Random random = new Random();
        mCurrentCommentSize = 5 + random.nextInt(10);//[5, 15]

        for(int i=0;i<mCurrentCommentSize;i++){
            String text = PictureTopicDummyDataGen.getFeedActionText();
            float x = random.nextInt(500);
            float y = random.nextInt(500);

            String avatar = PictureTopicDummyDataGen.getAvatar();
            BarrageProtos.PBFeedAction action = BarrageProtos.PBFeedAction.newBuilder().setAvatar(avatar).setText(text).setPosX(x).setPosY(y).build();

            feedActionList.add(action);
        }
        mMainWidget.setBarrageActions(feedActionList);
    }
}