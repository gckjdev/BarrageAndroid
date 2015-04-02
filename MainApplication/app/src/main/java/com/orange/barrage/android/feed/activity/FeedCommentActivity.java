package com.orange.barrage.android.feed.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.feed.ui.view.CircleColorView;
import com.orange.barrage.android.feed.ui.view.BarrageGridView;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.ui.topic.FeedActionWidget;
import com.orange.barrage.android.ui.topic.FeedMainWidget;
import com.orange.barrage.android.ui.topic.model.FeedModel;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.CompressColorUtil;
import com.orange.barrage.android.util.misc.ScreenUtil;
import com.orange.barrage.android.util.view.MoveViewParentRelativity;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class FeedCommentActivity extends BarrageCommonActivity implements View.OnClickListener {

    @InjectView(R.id.color_ring)
    LinearLayout mLayout;

    @InjectView(R.id.feed_main_widget)
    FeedMainWidget mFeedMainWidget;

    BarrageProtos.PBFeed mFeed;
    BarrageProtos.PBFeedAction.Builder mActionBuilder;

    @Inject
    FeedMission mFeedMission;

    @Inject
    UserManager mUserManager;


    private int mSelectColor = Color.WHITE;
    private int mColors[] = {Color.WHITE, 0xFFFF0000, 0xFF339900, 0xFFFFFF00,
            0xFF0099FF, 0xFF66CC33, 0xFFFF3366, 0xFF000066, 0xFFFF9900, 0xFF663399,
            0xFF33CCCC, 0xFF666600, 0XFF383838};

    private FeedActionWidget mCommentsEdit;
    private CircleColorView mCircleColorView;
    private BottonButtonModel mButtonMdoel = new BottonButtonModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_feed_comment, R.string.b_comment, R.string.b_send);

        // init top bar
        mTopBarView.setNavigationBackgroundChangeOtherType();
        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRight(v);
            }
        });
        initView();


    }


    private void initView() {
        FeedModel model = initData();
        if (model == null) {
            return;
        }

        //FIXME: need to init mCommentsEdit first
        int xy[] = getIntentIntArrays(HomeActivity.KEYSSCREENXY);

        mCommentsEdit = addViewCommentToMoveView(xy[0], xy[1]);
        //设置成可编辑
        mCommentsEdit.setType(FeedActionWidget.COMMENTS_EDITTEXT);

        for (int i = 0; i < mColors.length; i++) {
            mLayout.addView(CreateImageView(mColors[i]));
        }

        View linear = findViewById(R.id.linearLayout1);
        //下面按钮获取图标
        linear.setSelected(true);
        findViewById(R.id.imageButton1).setTag(linear);

        mFeedMainWidget.initActualWidth(ScreenUtil.getWidthPixels());
        mFeedMainWidget.setModel(model);

        //设置头像
        UserProtos.PBUser user = mUserManager.getUser();
        mCommentsEdit.setUser(user);
    }

    private FeedModel initData() {
        try {
            mFeed = BarrageProtos.PBFeed.parseFrom(getIntentByteArrays(HomeActivity.KEYSBYTE));
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "init feed comment data, parse data exception=" + e.toString());
        }

        if (mFeed == null) {
            return null;
        }

        FeedModel model = new FeedModel();
        model.setFeed(mFeed);

        //FIXME: Rollin init the action later, init it when publish
        // init action builder
        mActionBuilder = BarrageProtos.PBFeedAction.newBuilder();
        mActionBuilder.setFeedId(mFeed.getFeedId());
        mActionBuilder.setUser(mUserManager.getUser());
        mActionBuilder.setActionId("");
        mActionBuilder.setText("");
        mActionBuilder.setAvatar(mUserManager.getUser().getAvatar());

        return model;
    }

    private View CreateImageView(int color) {
        View v = LayoutInflater.from(this).inflate(R.layout.commite_color_view, null);
        CircleColorView ccv = (CircleColorView) v.findViewById(R.id.imageView1);

        if (mSelectColor == color) {
            ccv.setImageResource(R.drawable.y_comments_select);
            mCircleColorView = ccv;

        }

        ccv.setColor(color);
        ccv.setTag(color);
        ccv.setOnClickListener(this);
        return v;
    }

    //将回复的View加入到移动的View上面去
    private FeedActionWidget addViewCommentToMoveView(int left, int top) {
        if (mFeedMainWidget.getInnerView().getMoveView() == null) {
            return null;
        }
        FeedActionWidget comment = new FeedActionWidget(this);


        getMoveView().addView(comment, left, top);
        return comment;
    }

    private MoveViewParentRelativity getMoveView() {
        return mFeedMainWidget.getInnerView().getMoveView();
    }

    /**
     * 改变文本颜色
     *
     * @param color
     */
    private void changeTextColor(int color) {

        if (mCommentsEdit != null) {
            mCommentsEdit.setTextColor(color);
        }
    }

    //Show barrages
    public void onFeedActionWidgetShow(View v) {
        if (v.getTag() == null || v.getTag().equals("HideBarrage")) {
            v.setTag("ShowBarrage");
            //move to end
            mFeedMainWidget.getInnerView().moveToEnd();
            mFeedMainWidget.getInnerView().showAllBarrageActions();
        } else {
            v.setTag("HideBarrage");
            mFeedMainWidget.getInnerView().hideAllBarrageActions();
        }
    }

    //添加网格
    public void onGridViewShow(View v) {
        mFeedMainWidget.getInnerView().showOrCloseBarrageGridView();
//        BarrageGridView gridView = mFeedMainWidget.getInnerView().getGridView();
//        if (gridView == null) {
//            return;
//        }
//
//        if (v.getTag() == null || v.getTag().equals("o")) {
//            gridView.setVisibility(View.VISIBLE);
//            v.setTag("y");
//        } else {
//            gridView.setVisibility(View.GONE);
//            v.setTag("o");
//        }

    }

    public void onClickText(View v) {
        setSelectView(v, R.id.linearLayout1);
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    public void onClickGraffiti(View v) {
        setSelectView(v, R.id.linearLayout2);
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    public void onClickLabel(View v) {
        setSelectView(v, R.id.linearLayout3);
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    private void setSelectView(View v, int resId) {

        if (mButtonMdoel.v != null) {
            mButtonMdoel.v.setSelected(false);
        }
        if (v.getTag() == null) {
            v.setTag(findViewById(resId));
        }
        View parcent = (View) v.getTag();
        parcent.setSelected(true);
        mButtonMdoel.v = parcent;
    }

    class BottonButtonModel {
        View v;
    }

    @Override
    public void onClick(View v) {

        if (mCircleColorView != null) {
            mCircleColorView.setImageBitmap(null);
        }
        ((CircleColorView) v).setImageResource(R.drawable.y_comments_select);
        mCircleColorView = ((CircleColorView) v);
        int color = v.getTag() != null ? (int) v.getTag() : Color.BLACK;
        changeTextColor(color);
    }

    @Override
    public void onClickLeft(View v) {
        if (mCommentsEdit.getText().toString().trim().length() == 0)
            super.onClickLeft(v);
        else showRemindboxAlertDialog(new String[]{"是", "否"}, "提示", "你已经编辑了，是否退出", -1);
    }

    @Override
    public void onClickRight(View v) {

        if (mCommentsEdit.getText().toString().trim().length() == 0) {
            MessageCenter.postInfoMessage("你没有输入文字，不可以发表");
            return;
        }

        sendReply(mCommentsEdit.getText());
    }

    @Override
    public void onRemindItemClick(int position) {
        if (position == RemindboxAlertDialog.LEFTBUTTON) {
            finish();
        }
    }

    public void sendReply(String replyText) {
        String feedId = mFeed.getFeedId();
        String text = replyText;
        mActionBuilder.setText(replyText);

        MoveViewParentRelativity mComentRelative = mFeedMainWidget.getInnerView().getMoveView();

        Ln.d("publish comment on pos (%d, %d)", mComentRelative.getMoveingViewX(), mComentRelative.getMoveingViewY());
        mActionBuilder.setPosX(mComentRelative.getMoveingViewX());
        mActionBuilder.setPosY(mComentRelative.getMoveingViewY());

        int color = mCommentsEdit.getTextColor();
        int barrageColor = CompressColorUtil.toBarrageColor(color);
        mActionBuilder.setColor(barrageColor);

        final BarrageProtos.PBFeedAction action = mActionBuilder.build();

        mFeedMission.replyFeed(action, new FeedMissionCallbackInterface() {
            @Override
            public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                //need to add the Feed to current widget...
                List<BarrageProtos.PBFeedAction> feedActionLis = mFeedMainWidget.getModel().getFeedActionLis();
                feedActionLis.add(action);

                mFeedMainWidget.setModel(mFeedMainWidget.getModel());

                //add action
                finish();
                mFeedMainWidget.playFrom(0);

//                mFeedMainWidget.playFrom(feedActionLis.size() - 1);
            }

            @Override
            public void handleFailure(int errorCode) {
                // TODO failure
            }
        });
    }
}
