package com.orange.barrage.android.util.activity;

import android.os.Bundle;
import android.view.View;

import com.orange.barrage.android.BarrageAndroid;

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
        onCreate(savedInstanceState ,layoutresid ,-1 , getString(titleid) , rightid);
    }

    protected  void onCreate(Bundle savedInstanceState , int layoutresid ,int leftid ,  int titleid , int rightid){
        onCreate(savedInstanceState ,layoutresid ,leftid , getString(titleid) , rightid);
    }

    /**
     *
     * @param savedInstanceState
     * @param titleString 屏幕标题的字符串
     * @param rightid 右边按钮的图标资源
     */
    protected void onCreate(Bundle savedInstanceState,int layoutresid ,String titleString , int rightid ){
        onCreate(savedInstanceState , layoutresid , -1 , titleString , rightid);

    }

    protected void onCreate(Bundle saveInstanceState  , int layoutresid , int leftid , String titleString , int rightid){
        super.onCreate(saveInstanceState);
        mBarrageAndroid = (BarrageAndroid)getApplication();
        setContentView(layoutresid);

        // init top bar
        mTopBarView = new TopBarView(this);
        mTopBarView.setTitleText(titleString);
        mTopBarView.setRightButton(rightid);
        mTopBarView.setLeftButton(leftid);
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

        // init dialog
        mProgressDialog = new BarrageProgressDialog(this);
        initView();
    }



    protected void initView(){

    }


    public void setNewMessageNumber(String num){
        mTopBarView.setNumberText(num);
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
    public  void showProgress(String text){
        mProgressDialog.show(text);
    }

    /**
     * 关闭等待进度条
     */
    public  void dismissProgress(){
        mProgressDialog.close();
    }

    public void showTopProgress(){
        mTopBarView.showTopProgress();
    }

    public void dismissTopProgress(){
        mTopBarView.dismissTopProgress();
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
        finish();
    }

    public void onClickFinish(View v){
        finish();
    }
}
