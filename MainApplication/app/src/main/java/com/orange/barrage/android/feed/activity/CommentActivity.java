package com.orange.barrage.android.feed.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.ui.view.CircleColorView;
import com.orange.barrage.android.feed.ui.view.TableView;
import com.orange.barrage.android.ui.topic.model.Comment;
import com.orange.barrage.android.user.ui.view.CommentsView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.view.MoveViewParentRelativity;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class CommentActivity extends BarrageCommonActivity implements View.OnClickListener {

    @InjectView(R.id.color_ring)
    LinearLayout mLayout;

    @InjectView(R.id.commentFrame)
    MoveViewParentRelativity mCoomentRelative;

    @InjectView(R.id.tableview)
    TableView mTableView;


    private int colors[] = {Color.BLUE , Color.BLACK,Color.MAGENTA , Color.YELLOW , Color.DKGRAY,Color.BLUE , Color.BLACK,Color.MAGENTA , Color.YELLOW , Color.DKGRAY};
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
        mCommentsEdit = addViewCommentToMoveView(130,130);

        //设置成可编辑
        mCommentsEdit.setType(CommentsView.COMMENTS_EDITTEXT);

        for(int i = 0 ; i < colors.length ; i ++){
            mLayout.addView(CreateImageView(colors[i]));
        }
    }


    private View CreateImageView(int color){
        View v = LayoutInflater.from(this).inflate(R.layout.commite_color_view, null);
        CircleColorView ccv = (CircleColorView)v.findViewById(R.id.imageView1);
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
     * 文本颜色被改变
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

        if(mCircleColorView != null) mCircleColorView.setImageBitmap(null);
        ((CircleColorView)v).setImageResource(R.drawable.y_comments_select);
        mCircleColorView = ((CircleColorView)v);
        changeTextColor(v.getTag() != null ? (int) v.getTag() : -1);
    }





}
