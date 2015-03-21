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
import com.orange.barrage.android.feed.ui.view.TableView;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.ui.topic.PictureTopicMainInnerWidget;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.ui.topic.model.User;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.view.CommentsView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
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

    @InjectView(R.id.commentFrame)
    MoveViewParentRelativity mComentRelative;

    @InjectView(R.id.tableview)
    TableView mTableView;

    BarrageProtos.PBFeed mFeed;
    BarrageProtos.PBFeedAction.Builder mActionBuilder;

    @Inject
    FeedMission mFeedMission;

    @Inject
    UserManager mUserManager;

    private int mSelectColor = Color.WHITE;
    private int mColors[] = { Color.WHITE,0XFF383838  , 0XFF9E6BEA , 0XFF9EC138 , 0XFF6DA0F0 , 0XFFD28038 , 0xFFD2644D };
    private int mR = 14;

    private CommentsView mCommentsEdit;
    private CircleColorView mCircleColorView;
    private BottonButtonModel mButtonMdoel = new BottonButtonModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState , R.layout.activity_comments ,R.string.b_comment , R.string.b_send);

        // init top bar
        mTopBarView.setNavigationBackgroundChangeOtherType();
        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRight(v);
            }
        });
        //鱿鱼+2，冬菇鸡，纸包鸡,茶树菇，
        initView();
    }


    private void initView(){


        PictureTopicModel model = initData();
        if(model == null) {
            return;
        }

        //FIXME: need to init mCommentsEdit first
        int xy[] = getIntentIntArrays(HomeActivity.KEYSSCREENXY);

        mCommentsEdit = addViewCommentToMoveView(xy[0] , xy[1]);
        //设置成可编辑
        mCommentsEdit.setType(CommentsView.COMMENTS_EDITTEXT);

        for(int i = 0 ; i < mColors.length ; i ++){
            mLayout.addView(CreateImageView(mColors[i]));
        }

        View linear = findViewById(R.id.linearLayout1);
        //下面按钮获取图标
        linear.setSelected(true);
        findViewById(R.id.imageButton1).setTag(linear);

        mComentRelative.setImageUrl(model.getImageUrl());

        //设置头像
        UserProtos.PBUser user = mUserManager.getUser();
        mCommentsEdit.setIconUrl(user.getAvatar());
    }


    private PictureTopicModel initData(){

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



    private View CreateImageView(int color){
        View v = LayoutInflater.from(this).inflate(R.layout.commite_color_view, null);
        CircleColorView ccv = (CircleColorView)v.findViewById(R.id.imageView1);

        if(mSelectColor == color){
            ccv.setImageResource(R.drawable.y_comments_select);
            mCircleColorView = ccv;

        }

        ccv.setColor(color);
        ccv.setTag(color);
        ccv.setOnClickListener(this);
        return v;
    }


    //将回复的View加入到移动的View上面去
    private CommentsView addViewCommentToMoveView(int l , int t){
        if(mComentRelative == null) return null;
        CommentsView comment = new CommentsView(this);
        mComentRelative.addView(comment, l, t);

        return comment;
    }





    /**
     * 改变文本颜色
     * @param color
     */
    private void changeTextColor(int color){

        if(mCommentsEdit != null){
            mCommentsEdit.setTextColor(color);
        }
    }


    //是否移除背景颜色
    public void onClickRemoveBg(View v){
        if(v.getTag() == null || v .getTag().equals("Y")){
            //移除背景颜色
            v.setTag("N");
        }else {
            //添加背景颜色
            v.setTag("Y");
        }

    }




    //添加网格
    public void onClickTable(View v){
        if(mTableView == null)  return;
        if(v.getTag() == null || v.getTag().equals("o")) {
            mTableView.setVisibility(View.VISIBLE);
            v.setTag("y");
        }else{
            mTableView.setVisibility(View.GONE);
            v.setTag("o");
        }

    }


    public void onClickText(View v){
        setSelectView(v , R.id.linearLayout1);
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    public void onClickGraffiti(View v){
        setSelectView(v , R.id.linearLayout2);
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    public void onClickLabel(View v ){
        setSelectView(v , R.id.linearLayout3);
        MessageCenter.postInfoMessage("该功能正在开发");
    }


    private void setSelectView(View v  , int resId ){

        if (mButtonMdoel.v != null) {
            mButtonMdoel.v.setSelected(false);
        }
        if(v.getTag() == null){
            v.setTag(findViewById(resId));
        }
        View parcent = (View) v.getTag();
        parcent.setSelected(true);
        mButtonMdoel.v = parcent;
    }


    class BottonButtonModel{
        View v;
    }


    @Override
    public void onClick(View v) {

        if(mCircleColorView != null){
            mCircleColorView.setImageBitmap(null);
        }
        ((CircleColorView)v).setImageResource(R.drawable.y_comments_select);
        mCircleColorView = ((CircleColorView)v);
        int color = v.getTag() != null ? (int)v.getTag(): Color.BLACK;
        changeTextColor(color);

        mActionBuilder.setColor(color);


    }


    @Override
    public void onClickRight(View v) {
        sendReply(mCommentsEdit.getText());
    }

    public void sendReply(String replyText){

        String feedId = mFeed.getFeedId();
        String text = replyText;
        float x = 20;
        float y = 20;

        // set action builder info
        mActionBuilder.setPosX(x);
        mActionBuilder.setPosY(y);
        mActionBuilder.setText(replyText);

        mActionBuilder.setPosX(mComentRelative.getMoveingViewX());
        mActionBuilder.setPosY(mComentRelative.getMoveingViewY());

        BarrageProtos.PBFeedAction action = mActionBuilder.build();

        mFeedMission.replyFeed(action, new FeedMissionCallbackInterface() {
            @Override
            public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                finish();
            }

            @Override
            public void handleFailure(int errorCode) {
                // TODO failure
            }
        });
    }
}
