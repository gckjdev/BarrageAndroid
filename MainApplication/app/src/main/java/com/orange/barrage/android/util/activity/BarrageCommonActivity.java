package com.orange.barrage.android.util.activity;


import com.orange.barrage.android.BarrageAndroid;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import roboguice.activity.RoboActivity;

import com.orange.barrage.android.BarrageAndroid;
import com.orange.barrage.android.friend.mission.FriendMission;
import com.orange.barrage.android.friend.mission.TagMission;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.orange.protocol.message.UserProtos;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.util.Ln;

/**
 * Created by youjiannuo on 2015/3/3.
 */
public class BarrageCommonActivity extends RoboActivity implements RemindboxAlertDialog.OnClickListener {

    /* 进度条 */
    private BarrageProgressDialog mProgressDialog;

    /* Application */
    protected BarrageAndroid mBarrageAndroid;

    /* 导航栏 */
    protected TopBarView mTopBarView;

    /*获取intent携带的值*/
    private IntentUtil mIntentUtil;

    @Inject
    protected UserMission mUserMission;

    @Inject
    protected FriendMission mFriendMission;

    @Inject
    protected TagMission mTagMission;


    protected RemindboxAlertDialog mRemindboxAlterDialong;

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


    }



    @Override
    protected void onStart() {
        super.onStart();
        ContextManager.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContextManager.init(this);
    }

    protected IntentUtil getIntentUtil(){
        return mIntentUtil == null ? new IntentUtil(this) : mIntentUtil;
    }

    public String[] getIntentArraysString(String key){
        return getIntentUtil().getIntentArrayString(key);
    }

    public String getIntentString(String key){
        return getIntentUtil().getIntentString(key);
    }

    public Parcelable getIntentParcelable(String key){
        return getIntentUtil().getIntentParcelable(key);
    }

    public int getIntentInt(String key ,int defaultValue){
        return  getIntentUtil().getIntentInt(key , defaultValue);
    }

    public byte[] getIntentByteArrays(String key ){
        return getIntentUtil().getIntentByteArrays(key);
    }

    public int[] getIntentIntArrays(String key){
        return  getIntentUtil().getIntentIntArray(key);
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




    public void onClickFinish(View v){
        finish();
    }



    /**
     * 开启提醒框
     */
    protected  void showRemindboxAlertDialog(String button[] , String title , String Message ,int icon ,RemindboxAlertDialog.OnClickListener l){
        if(mRemindboxAlterDialong == null){
            mRemindboxAlterDialong = new RemindboxAlertDialog(this);
        }

        mRemindboxAlterDialong.show(button , title , Message, icon , l);
    }

    /**
     * 开启提醒框
     */
     protected void showRemindboxAlertDialog(String button[], String title, String message, int icon){
         showRemindboxAlertDialog(button , title , message , icon , this);
     }

    /**
     * 获取好友所属的标签
     * @param pbUser
     * @param tagManager
     * @return
     */
    protected List<UserProtos.PBUserTag> getTag( UserProtos.PBUser pbUser , TagManager tagManager){
        List<UserProtos.PBUserTag> pbUserTags = new ArrayList<>();
        if(pbUser == null || tagManager == null) return pbUserTags;

        UserProtos.PBUserTagList pbUserTagList = tagManager.allTags();

        for(UserProtos.PBUserTag pbUserTag : pbUserTagList.getTagsList()){

            List<UserProtos.PBUser> pbUserList = pbUserTag.getUsersList();

            for(UserProtos.PBUser pb : pbUserList){
                if(pb.getUserId().equals(pbUser.getUserId())){
                    pbUserTags.add(pbUserTag);
                    break;
                }
            }

        }
        return pbUserTags;
    }

    /**
     * 点击提醒框，可以继承这个方法
     * @param position
     */
    @Override
    public void onRemindItemClick(int position) {

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


}
