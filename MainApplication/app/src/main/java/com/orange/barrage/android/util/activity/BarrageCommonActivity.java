package com.orange.barrage.android.util.activity;




import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import roboguice.activity.RoboActivity;

import com.orange.barrage.android.BarrageAndroid;
import com.orange.barrage.android.R;

/**
 * Created by youjiannuo on 2015/3/3.
 */
public class BarrageCommonActivity extends RoboActivity  {

    /*进度条*/
    private ProgressDialogshow mProgressDialog;

    /*Application*/
    protected BarrageAndroid mBarrageAndroid;



    /**
     *
     * @param savedInstanceState
     * @param layoutresid 布局资源
     * @param titleid  屏幕标题的字符串资源
     * @param rightid  右边按钮的图标资源
     */
    protected  void onCreate(Bundle savedInstanceState , int layoutresid, int titleid , int rightid){
        onCreate(savedInstanceState ,layoutresid , getString(titleid) , rightid);
    }




    /**
     *
     * @param savedInstanceState
     * @param titleString 屏幕标题的字符串
     * @param rightid 右边按钮的图标资源
     */
    protected void onCreate(Bundle savedInstanceState,int layoutresid ,String titleString , int rightid ){
        super.onCreate(savedInstanceState);

        mBarrageAndroid = (BarrageAndroid)getApplication();

        setContentView(layoutresid);
        setTitleText(titleString);
        setRightButton(rightid);
    }


    protected  void setNavigationBackgroundChangeOtherType(){

        ((RelativeLayout)findViewById(R.id.top_main)).setBackgroundColor(Color.rgb(12,12,12));

    }


    /**
     * 设置标题
     * @param resid
     */
    public void setTitleText(int resid){
        setTitleText(getString(resid));
    }
    /**
     * 设置标题
     * @param s
     */
    public void setTitleText(String s){
        TextView mTitle = ((TextView)findViewById(R.id.top_title));
        if(mTitle != null)
            mTitle.setText(s);
    }

    public void setRightButton(int resid){
        if(resid < 0) return ;

        try {
            Drawable drawable = getDrawable(resid);
            ImageButton image = ((ImageButton)(findViewById(R.id.top_right)));
            image.setImageDrawable(drawable);
            image.setVisibility(View.VISIBLE);
        }catch (Exception e){
            TextView tv = (TextView)findViewById(R.id.top_right_text);
            tv.setText(getString(resid));
            tv.setVisibility(View.VISIBLE);
        }


    }

    public String getIntentString(String key){
        return getIntent().getStringExtra(key);
    }

    public int getIntentInt(String key ,int defaultValue){
        return getIntent().getIntExtra(key , defaultValue);
    }



    /**
     * 打开等待进度条
     * @param text
     */
    protected  void progresShow(String text){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialogshow(this , "", text);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭等待进度条
     */
    protected  void progresClose(){
        if(mProgressDialog != null)
            mProgressDialog.close();
    }


    /**
     * 点击按钮
     * @param v
     */
    public void onClickRight(View v){  }

    public void onClickFinish(View v){
        finish();
    }
}
