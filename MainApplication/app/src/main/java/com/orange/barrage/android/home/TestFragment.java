package com.orange.barrage.android.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.ui.topic.PictureTopicMainWidget;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.ToastUtil;
import com.orange.barrage.android.util.persistent.LevelDBTestDAO;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

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

    @InjectView(R.id.test_submit_button)
    Button mSubmitFeedButton;

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

        mSubmitFeedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: test code
                final Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                final String text = "Hello";
                final List<UserProtos.PBUser> toUsers = new ArrayList<UserProtos.PBUser>();
                toUsers.add(mUserManager.getUser());
                mFeedMission.createFeed(image, text, toUsers, new FeedMissionCallbackInterface(){

                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                        ToastUtil.showToastMessage(ContextManager.getContext(), "handleSuccess submit", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void handleFailure(int errorCode) {
                        ToastUtil.showToastMessage(ContextManager.getContext(), "handleFailure submit:"+ errorCode, Toast.LENGTH_SHORT);
                    }
                });


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

    private void initMainWidget(){


        mMainWidget.setImangeURL("http://115.29.249.57/uc_server/avatar.php?uid=2&size=middle");
        mMainWidget.setSubtitle("Subtitle Text");

        List<BarrageProtos.PBFeedAction> feedActionList = Lists.newArrayList();

        String avatar = "http://www.ixinde.net/static/image/common/common_1_usergroup_icon.gif";

        for(int i=0;i<10;i++){
            String text = "text"+i;
            float x = 40 * i;
            float y = 40 * i;

            BarrageProtos.PBFeedAction action = BarrageProtos.PBFeedAction.newBuilder().setAvatar(avatar).setText(text).setPosX(x).setPosY(y).build();

            feedActionList.add(action);
        }
        mMainWidget.setBarrageActions(feedActionList);
    }
}