package com.orange.barrage.android.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.feed.ui.FeedPhotoSourceSelectionLayout;
import com.orange.barrage.android.ui.topic.PictureTopicMainWidget;
import com.orange.barrage.android.ui.topic.data.dummy.PictureTopicDummyDataGen;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.misc.PopupWindowUtil;
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

import static com.orange.barrage.android.util.misc.PopupWindowUtil.*;

/**
 * Created by pipi on 15/1/6.
 */
public class TestFragment extends RoboFragment {

    @Inject
    LevelDBTestDAO mLevelDBTestDAO;

    @Inject
    FeedMission mFeedMission;

    @Inject
    UserMission mUserMission;

    @Inject
    UserManager mUserManager;

    @InjectView(R.id.test_button)
    Button mTestButton;

    @InjectView(R.id.test_db_button)
    Button mTestDBButton;

    @InjectView(R.id.test_create_feed_button)
    Button mCreateFeedButton;

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
        View inflate = inflater.inflate(R.layout.fragment_test, container, false);

        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCreateFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedPhotoSourceSelectionLayout sourceSelection = new FeedPhotoSourceSelectionLayout(ContextManager.getContext());
                //FIXME: screen size, change to dp px later
                PopupWindow window = PopupWindowUtil.getPopupWindow(sourceSelection, new ColorDrawable(Color.RED), ContextManager.getContext(), 600, 400);
                window.showAtLocation(TestFragment.this.getView(), Gravity.CENTER, 0, 0);
            }
        });

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

        mTestDBButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!mUserManager.hasUser()) {
                    String email = String.format("test%d@163.com", DateUtil.getNowTime());
                    mUserMission.regiseterUserByEmail(email, "password", null, new UserMissionCallback() {
                        @Override
                        public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                            ToastUtil.showToastMessage(ContextManager.getContext(), "new user added", Toast.LENGTH_SHORT);
                        }
                    });
                }else{
                    ToastUtil.showToastMessage(ContextManager.getContext(), "user already existed", Toast.LENGTH_SHORT);
                }
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

        final Handler handler = new Handler();
        final List<Runnable> runnings = new ArrayList<>();

        mMoveToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                float start = ((float)mCurrentCommentSize) *  random.nextFloat();
                float end = ((float)mCurrentCommentSize) *  random.nextFloat();
                ToastUtil.showToastMessage(ContextManager.getContext(), String.format("from %1$.3f to %2$.3f", start,end), Toast.LENGTH_SHORT);

                float frameCount = 100;

                for(Runnable r: runnings){
                    handler.removeCallbacks(r);
                }
                runnings.clear();

                for(int i=0;i<frameCount ;i++){
                    final float currentProgress = start + i*(end-start)/frameCount;
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            mMainWidget.moveTo(currentProgress);

                            ToastUtil.showToastMessage(ContextManager.getContext(), String.format("current %1$.3f", currentProgress), Toast.LENGTH_SHORT);
                        }
                    };
                    runnings.add(r);

                    handler.postDelayed(r, i * 100);
                }

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

    private int mCommentLocationThreshold = 800;

    private void initMainWidget(){
        String imageURL = PictureTopicDummyDataGen.getImange();
        mMainWidget.setImangeURL(imageURL);
        mMainWidget.setSubtitle("Subtitle Text");

        List<BarrageProtos.PBFeedAction> feedActionList = Lists.newArrayList();

        Random random = new Random();
        mCurrentCommentSize = 5 + random.nextInt(10);//[5, 15]

        for(int i=0;i<mCurrentCommentSize;i++){
            String text = PictureTopicDummyDataGen.getFeedActionText();
            float x = mCommentLocationThreshold * random.nextFloat();
            float y =  mCommentLocationThreshold * random.nextFloat();

            String avatar = PictureTopicDummyDataGen.getAvatar();
            BarrageProtos.PBFeedAction action = BarrageProtos.PBFeedAction.newBuilder().setAvatar(avatar).setText(text).setPosX(x).setPosY(y).build();

            feedActionList.add(action);
        }
        mMainWidget.setBarrageActions(feedActionList);
    }
}