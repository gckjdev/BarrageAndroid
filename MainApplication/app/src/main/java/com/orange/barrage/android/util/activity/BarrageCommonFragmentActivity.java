package com.orange.barrage.android.util.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.litl.leveldb.NotFoundException;
import com.orange.barrage.android.BarrageAndroid;
import com.orange.barrage.android.R;

import roboguice.activity.RoboFragmentActivity;

/**
 * Created by youjiannuo on 2015/3/9.
 */
public class BarrageCommonFragmentActivity extends RoboFragmentActivity {

    /*进度条*/
    private BarrageProgressDialog mProgressDialog;

    /*Application*/
    protected BarrageAndroid mBarrageAndroid;

    /* 导航栏 */
    protected TopBarView mTopBarView;

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

        // init dialog
        mProgressDialog = new BarrageProgressDialog(this);

        // init top bar
        mTopBarView = new TopBarView(this);
        mTopBarView.setTitleText(titleString);
        mTopBarView.setRightButton(rightid);
        mTopBarView.hideLeftButton();
        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRight(v);
            }
        });
        mTopBarView.setOnClickLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeft(v);
            }
        });


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
    protected  void showProgress(String text){
        mProgressDialog.show(text);
    }

    /**
     * 关闭等待进度条
     */
    protected  void dismissProgress(){
        mProgressDialog.close();
    }

    /**
     * 点击导航栏右边按钮可继承本方法
     * @param v
     */
    public void onClickRight(View v){
    }

    /**
     * 点击导航栏左边按钮可继承本方法
     * @param v
     */
    public void onClickLeft(View v){
    }

    public void onClickFinish(View v){
        finish();
    }
}
