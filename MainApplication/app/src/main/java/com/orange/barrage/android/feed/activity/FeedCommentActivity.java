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
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.ScreenUtil;
import com.orange.barrage.android.util.view.MoveViewParentRelativity;
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

    @InjectView(R.id.barrage_view)
    FeedMainWidget mBarrageView;

    BarrageProtos.PBFeed mFeed;
    BarrageProtos.PBFeedAction.Builder mActionBuilder;

    @Inject
    FeedMission mFeedMission;

    @Inject
    UserManager mUserManager;

    private int mSelectColor = Color.WHITE;
    private int mColors[] = {Color.WHITE, 0XFF383838, 0XFF9E6BEA, 0XFF9EC138, 0XFF6DA0F0, 0XFFD28038, 0xFFD2644D};

    private FeedActionWidget mCommentsEdit;
    private CircleColorView mCircleColorView;
    private BottonButtonModel mButtonMdoel = new BottonButtonModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_comments, R.string.b_comment, R.string.b_send);

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
        PictureTopicModel model = initData();
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

        mBarrageView.initActualWidth(ScreenUtil.getWidthPixels());
        mBarrageView.setModel(model);

        //设置头像
        UserProtos.PBUser user = mUserManager.getUser();
        mCommentsEdit.setUser(user);
    }

    private PictureTopicModel initData() {

        try {
            mFeed = BarrageProtos.PBFeed.parseFrom(getIntentByteArrays(HomeActivity.KEYSBYTE));
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "init feed comment data, parse data exception=" + e.toString());
        }

        if (mFeed == null)
            return null;

        PictureTopicModel model = new PictureTopicModel();
        model.setFeed(mFeed);

        // init aciton builder
        mActionBuilder = BarrageProtos.PBFeedAction.newBuilder();
        mActionBuilder.setFeedId(mFeed.getFeedId());

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
    private FeedActionWidget addViewCommentToMoveView(int l, int t) {
        if (mBarrageView.getInnerView().getMoveView() == null) {
            return null;
        }
        FeedActionWidget comment = new FeedActionWidget(this);
        getMoveView().addView(comment, l, t);
        return comment;
    }

    private MoveViewParentRelativity getMoveView() {
        return mBarrageView.getInnerView().getMoveView();
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

    //是否移除背景颜色
    public void onClickRemoveBg(View v) {
        if (v.getTag() == null || v.getTag().equals("Y")) {
            //移除背景颜色
            v.setTag("N");
        } else {
            //添加背景颜色
            v.setTag("Y");
        }

    }

    public void onFeedActionWidgetShow(View v){
        if (v.getTag() == null || v.getTag().equals("HideBarrage")) {
            v.setTag("ShowBarrage");
            //move to end
            mBarrageView.getInnerView().moveToEnd();
            mBarrageView.getInnerView().showAllBarrageActions();
        } else {
            //添加背景颜色
            v.setTag("HideBarrage");
            mBarrageView.getInnerView().hideAllBarrageActions();
        }
    }

    //添加网格
    public void onGridViewShow(View v) {
        BarrageGridView gridView = mBarrageView.getInnerView().getGridView();
        if (gridView == null) {
            return;
        }

        if (v.getTag() == null || v.getTag().equals("o")) {
            gridView.setVisibility(View.VISIBLE);
            v.setTag("y");
        } else {
            gridView.setVisibility(View.GONE);
            v.setTag("o");
        }

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

        mActionBuilder.setColor(color);
    }

    @Override
    public void onClickRight(View v) {
        sendReply(mCommentsEdit.getText());
    }

    public void sendReply(String replyText) {
        String feedId = mFeed.getFeedId();
        String text = replyText;
        mActionBuilder.setText(replyText);

        MoveViewParentRelativity mComentRelative = mBarrageView.getInnerView().getMoveView();

        Ln.d("publish comment on pos (%d, %d)", mComentRelative.getMoveingViewX(), mComentRelative.getMoveingViewY());
        mActionBuilder.setPosX(mComentRelative.getMoveingViewX());
        mActionBuilder.setPosY(mComentRelative.getMoveingViewY());

        final BarrageProtos.PBFeedAction action = mActionBuilder.build();

        mFeedMission.replyFeed(action, new FeedMissionCallbackInterface() {
            @Override
            public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                //FIXME: need to add the Feed to current widget...
                //add action
                finish();
            }

            @Override
            public void handleFailure(int errorCode) {
                // TODO failure
            }
        });
    }
}
