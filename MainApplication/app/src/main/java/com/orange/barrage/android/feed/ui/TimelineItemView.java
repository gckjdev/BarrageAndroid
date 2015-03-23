package com.orange.barrage.android.feed.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.event.StartActivityFeedPublishedOtherPlatformEvent;
import com.orange.barrage.android.feed.mission.ShowFriendIconView;
import com.orange.barrage.android.ui.topic.FeedMainWidget;
import com.orange.barrage.android.ui.topic.model.FeedModel;

import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.misc.ScreenUtil;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by pipi on 15/1/7.
 */
public class TimelineItemView extends LinearLayout implements View.OnClickListener {

    private ImageButton mShareButton;
    private LinearLayout mLayoutIcon;
    private TextView mTimeTextView ;
    private ImageButton mPlayerButton;
    private FeedMainWidget mFeedMainWidget;
    private ImageButton mDropDownImageButton;
    private ShowFriendIconView mShowFriendIconView;
    private List<UserProtos.PBUser> mToUsers;
    private View mView;
    //FIXME: Rollin, unused layout
    private RelativeLayout mMainRelativeLayout;

    public TimelineItemView(Context context) {
        super(context);
        initView(context);
    }

    public void initView(Context c){
        mView = LayoutInflater.from(ContextManager.getContext()).inflate(R.layout.view_timeline_list_item, this);
        mShareButton = (ImageButton)mView.findViewById(R.id.shareButton);
        mPlayerButton = (ImageButton)mView.findViewById(R.id.playerButton);

        mFeedMainWidget = (FeedMainWidget)mView.findViewById(R.id.feed_main_widget);
        mFeedMainWidget.initActualWidth(ScreenUtil.getWidthPixels());

        mLayoutIcon  = (LinearLayout)mView.findViewById(R.id.iconlayout);
        mTimeTextView = (TextView)mView.findViewById(R.id.timeline_item_date);
        mDropDownImageButton = (ImageButton) mView.findViewById(R.id.dropDownButton);
        mMainRelativeLayout = (RelativeLayout) mView.findViewById(R.id.iconMainRelativrLayout);

        mDropDownImageButton.setOnClickListener(this);
        mPlayerButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);
    }

    public void setModel(FeedModel model) {
        if(model == null) {
            return;
        }
        //  show date
        int createDate = model.getFeed().getDate();
        mTimeTextView.setText(DateUtil.dateFormatToString(createDate, getContext()));

        //set icon
        setIcon(model);

        //set user list
        List<UserProtos.PBUser> toUsers = model.getFeed().getToUsersList();
        mFeedMainWidget.setModel(model);
    }




    private void setIcon(FeedModel model){
        mToUsers = model.getFeed().getToUsersList();
        if(mToUsers == null) {
            return;
        }

        setIconVisible();
        int n = mToUsers.size() > mLayoutIcon.getChildCount() ? mLayoutIcon.getChildCount() : mToUsers.size();
        for(int i = 0 ; i < n ; i ++){
            ((UserAvatarView)mLayoutIcon.getChildAt(i)).loadUser(mToUsers.get(i));
        }
    }

    private void setIconVisible(){
        for(int i = 0 ; i < mLayoutIcon.getChildCount() ; i ++)
            mLayoutIcon.getChildAt(i).setVisibility(View.GONE);
    }

    /**
     * 分享东西
     * @param v
     */
    public void onClickPublish(View v){
        StartActivityFeedPublishedOtherPlatformEvent event = new StartActivityFeedPublishedOtherPlatformEvent();
        FeedModel model = mFeedMainWidget.getModel();
        event.setByteArray( model.getFeed().toByteArray());
        EventBus.getDefault().post(event);
    }

    /**
     * 播放
     *
     */
    public void onClickPlayer(View v){
        mFeedMainWidget.play();
    }


    /**
     * 下拉头像
     * @param v
     */
    public void onClickDraoDown(View v){
        mShowFriendIconView = new ShowFriendIconView((Activity)ContextManager.getContext());
        mShowFriendIconView.show(v, mToUsers);
    }

    @Override
    public void onClick(View v) {
        if(mShareButton == v)  onClickPublish(v);
        else if (mPlayerButton == v) onClickPlayer(v);
        else if (mDropDownImageButton == v) onClickDraoDown(v);
    }

}
