package com.orange.barrage.android.util.activity;


import android.os.Bundle;
import android.view.View;

import com.orange.barrage.android.BarrageAndroid;
import com.orange.barrage.android.friend.mission.FriendMission;
import com.orange.barrage.android.user.mission.UserMission;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;

/**
 * Created by youjiannuo on 2015/3/3.
 */
public class BarrageCommonActivity extends RoboActivity  {

    /* 进度条 */
    private BarrageProgressDialog mProgressDialog;

    /* Application */
    protected BarrageAndroid mBarrageAndroid;

    /* 导航栏 */
    protected TopBarView mTopBarView;

    @Inject
    protected UserMission mUserMission;

    @Inject
    protected FriendMission mFriendMission;


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

        // init top bar
        mTopBarView = new TopBarView(this);
        mTopBarView.setTitleText(titleString);
        mTopBarView.setRightButton(rightid);

        // init dialog
        mProgressDialog = new BarrageProgressDialog(this);
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
     * 点击按钮
     * @param v
     */
    public void onClickRight(View v){
    }

    public void onClickFinish(View v){
        finish();
    }
}
