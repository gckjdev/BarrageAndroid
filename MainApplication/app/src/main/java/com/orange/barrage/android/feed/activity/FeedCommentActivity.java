package com.orange.barrage.android.feed.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.view.CircleColorView;
import com.orange.barrage.android.feed.ui.view.TableView;
import com.orange.barrage.android.ui.topic.PictureTopicMainInnerWidget;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.barrage.android.user.ui.view.CommentsView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.view.MoveViewParentRelativity;
import com.orange.protocol.message.BarrageProtos;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class FeedCommentActivity extends BarrageCommonActivity implements View.OnClickListener {

    @InjectView(R.id.color_ring)
    LinearLayout mLayout;

    @InjectView(R.id.commentFrame)
    MoveViewParentRelativity mCoomentRelative;

    @InjectView(R.id.tableview)
    TableView mTableView;


    private int mSelectColor = 0XFF383838;
    private int mColors[] = {0XFF383838 , Color.WHITE , 0XFF9E6BEA , 0XFF9EC138 , 0XFF6DA0F0 , 0XFFD28038 , 0xFFD2644D };
    private int mR = 14;

    private CommentsView mCommentsEdit;
    private CircleColorView mCircleColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState , R.layout.activity_comments ,R.string.b_comment , R.string.b_send);
        mTopBarView.setNavigationBackgroundChangeOtherType();
        initView();

    }


    private void initView(){


        PictureTopicModel model = initData();
        if(model == null) {
            return;
        }
        //FIXME: need to init mCommentsEdit first
        int xy[] = getIntentIntArrays(PictureTopicMainInnerWidget.KEYSSCREENXY);

        mCommentsEdit = addViewCommentToMoveView(xy[0] , xy[1]);
        //设置成可编辑
        mCommentsEdit.setType(CommentsView.COMMENTS_EDITTEXT);
        for(int i = 0 ; i < mColors.length ; i ++){
            mLayout.addView(CreateImageView(mColors[i]));
        }


    }


    private PictureTopicModel initData(){


        BarrageProtos.PBFeed newFeed = null;
        try {
            newFeed = BarrageProtos.PBFeed.parseFrom(getIntentByteArrays(PictureTopicMainInnerWidget.KEYSBYTE));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        if(newFeed == null) return null;
        PictureTopicModel modle = new PictureTopicModel();
        modle.setFeed(newFeed);
        return modle;
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
        if(mCoomentRelative == null) return null;
        CommentsView comment = new CommentsView(this);
        mCoomentRelative.addView(comment , l , t);

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
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    public void onClickGraffiti(View v ){
        MessageCenter.postInfoMessage("该功能正在开发");
    }

    public void onClickLabel(View v ){
        MessageCenter.postInfoMessage("该功能正在开发");
    }


    @Override
    public void onClick(View v) {

        if(mCircleColorView != null){
            mCircleColorView.setImageBitmap(null);
        }
        ((CircleColorView)v).setImageResource(R.drawable.y_comments_select);
        mCircleColorView = ((CircleColorView)v);
        changeTextColor(v.getTag() != null ? (int)v.getTag(): Color.BLACK);
    }


    @Override
    public void onClickRight(View v) {

       //发生评价

    }
}
