package com.orange.barrage.android.feed.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.event.StartActivityFeedPublishedOtherPlatformEvent;
import com.orange.barrage.android.feed.activity.FeedPublishedOtherPlatform;
import com.orange.barrage.android.feed.mission.ShowFriendIconView;
import com.orange.barrage.android.feed.mission.ShowPublishFeedView;
import com.orange.barrage.android.ui.topic.PictureTopicMainWidget;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;

import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.misc.ScreenUtil;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import de.greenrobot.event.EventBus;
import roboguice.util.Ln;

/**
 * Created by pipi on 15/1/7.
 */
public class TimelineItemView extends LinearLayout implements View.OnClickListener {


//    private ImageView barrageView;
    private ImageButton mShareButton;
    private LinearLayout mLayoutIcon;
    private TextView mTimeTextView ;
    private ImageButton mPlayerButton;
    private PictureTopicMainWidget mBarrageWidget;
    private ImageButton mDropDownImageButton;
    private RelativeLayout mMainRelativeLayout;


    private List<UserProtos.PBUser> mToUsers;

    private Context mContext;
    private Activity mActivity;
    private ShowFriendIconView mShowFriendIconView;
    private View mView;

    public TimelineItemView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public void initView(Context c){
        this.mContext = c;
        mView = LayoutInflater.from(this.mContext).inflate(R.layout.view_timeline_list_item, this);
//        barrageView = (ImageView) view.findViewById(R.id.timeline_item_barage_image);
        mShareButton = (ImageButton)mView.findViewById(R.id.shareButton);
        mPlayerButton = (ImageButton)mView.findViewById(R.id.playerButton);

        mBarrageWidget = (PictureTopicMainWidget)mView.findViewById(R.id.timeline_item_barage_view);
        //calculate the height
        float expectWidth = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_width);
        float actualWidth = ScreenUtil.getWidthPixels();

        float expectHeight = getResources().getDimension(R.dimen.y_barrage_main_inner_widget_height);
        float factor = actualWidth/expectWidth;
        float actualHeight = expectHeight * factor;


        mBarrageWidget.setSize((int)actualWidth, (int)actualHeight);

        mLayoutIcon  = (LinearLayout)mView.findViewById(R.id.iconlayout);
        mTimeTextView = (TextView)mView.findViewById(R.id.timeline_item_date);
        mDropDownImageButton = (ImageButton) mView.findViewById(R.id.dropDownButton);
        mMainRelativeLayout = (RelativeLayout) mView.findViewById(R.id.iconMainRelativrLayout);

        mDropDownImageButton.setOnClickListener(this);
        mPlayerButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);

    }


    public void setModel(PictureTopicModel model , Activity activity) {
        // TODO
        if(model == null) return;
        this.mActivity = activity;
//        barrageView.setImageResource(R.drawable.tab_home);

//        ((TextView)findViewById(R.id.titleText)).setText(model.getSubtitleText()+"djsfjsdfs");

//        Picasso.with(mContext).load(model.getImageUrl()).
//                placeholder(R.drawable.tab_home).
//                error(R.drawable.tab_friend).into(barrageView);

        // TODO show date
        int createDate = model.getFeed().getDate();
        mTimeTextView.setText(DateUtil.dateFormatToString(createDate, getContext()));

        //set icon
        setIcon(model);

        // TODO
        List<UserProtos.PBUser> toUsers = model.getFeed().getToUsersList();

        mBarrageWidget.setModel(model);

    }




    private void setIcon(PictureTopicModel model){
        mToUsers = model.getFeed().getToUsersList();
        if(mToUsers == null) return;

//        if(mToUsers.size() <= 5 ) mDropDownImageButton.setVisibility(GONE);

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
    public void onClickShare(View v){
        EventBus.getDefault().post(new StartActivityFeedPublishedOtherPlatformEvent());
    }

    /**
     * 播放
     *
     */
    public void onClickPlayer(View v){
        mBarrageWidget.play();
    }


    /**
     * 下拉头像
     * @param v
     */
    public void onClickDraoDown(View v){

//        int resource = R.drawable.y_zhuche_xiala;
//
//        if(v.getTag() == null || v.getTag().equals("C")) {
//            resource = R.drawable.y_zhuce_shouhui;
//            v.setTag("O");
//        }else{
//            v.setTag("C");
//        }
//        ((ImageButton)v).setImageResource(resource);

        if (mShowFriendIconView == null)
            mShowFriendIconView = new ShowFriendIconView(mActivity);
        mShowFriendIconView.show(v, mToUsers);

    }

    @Override
    public void onClick(View v) {
        if(mShareButton == v)  onClickShare(v);
        else if (mPlayerButton == v) onClickPlayer(v);
        else if (mDropDownImageButton == v) onClickDraoDown(v);
    }

}
