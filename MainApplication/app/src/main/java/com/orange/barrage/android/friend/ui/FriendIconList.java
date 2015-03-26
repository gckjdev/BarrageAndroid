package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.activity.FriendListSelectActivity;
import com.orange.barrage.android.friend.mission.callback.AddTagCallback;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconList extends LinearLayout  {

    //普通头像
    public static final int ICON_ORDINARY = 0;
    //显示删除和添加的按钮
    public static final int ICON_ADD_AND_DELETE_BUTTON = 1;
    //隐藏添加删除和添加头像按钮
    public static final int ICON_HIDDEN_ADD_AND_DELETE_BUTTON = 4;
    //头像右上方有删除按钮
    public static final int ICON_SHOW_RIGHT_TOP_DELETE = 2;
    //隐藏右上方按钮图标
    public static final int ICON_HIDDEN_RIGHT_TOP_DELETE = 3;


    public static final int TAG_IS_ALTER = 0x12;

    public static final int TAG_NO_ALTER = 0x11;

    public static final int TAG_IS_DELETE = 0x13;

    public static final int TAG_IS_CREATE = 0x14;

    public static final String TABKEY = "1";
    public static final String TABSTATEKEY = "2";


    private GridView mGridView;

    private TextView mPeopleNum;

    private TagManager mTagManager;

    private String mTabId;

    private UserProtos.PBUserTag mPBUserTag;
    private UserProtos.PBUserTag mNewPBUserTag;
    private UserProtos.PBUserTag.Builder mBuilder;
//    private UserProtos.PBUserTag.Builder mOldBuilder;

    private  List<UserProtos.PBUser> mOldPbuserLists;

    private Activity mActivity;

    private OnClickItemListener mL;
    FriendIconListAdapter mAdapter;

    //是否修改了
    private boolean mIsAlter = false;


    private String mText = "成员";

//    private GridView mGridView;

    public FriendIconList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

//    private void initView(){
//        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_friends_icon_list , null);
//
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT);
//
//        addView(v , params);
//
//    }

    private void initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.view_friend_icon_and_textview , this);

        mPeopleNum = (TextView) findViewById(R.id.chengyuan);
        mGridView = (GridView) findViewById(R.id.frinedIconGridView);

    }



    //初始化数据
    //一定要被调用
    public UserProtos.PBUserTag initData(TagManager tagManager , String tabId , Activity activity){
        mTagManager = tagManager;
        mTabId = tabId;
        mActivity = activity;

        mPBUserTag = mTagManager.getTagById(mTabId);

        if (mPBUserTag != null) {
            setUsers(mPBUserTag.getUsersList(), activity);
            setMenbertext(mPBUserTag.getUsersList().size());
//            mBuilder = UserProtos.PBUserTag.newBuilder(mPBUserTag);
        } else{
//            mBuilder = UserProtos.PBUserTag.newBuilder();
//            mBuilder.setTid("sasd");
            setMenbertext(0);
        }

        return mPBUserTag;
    }


    public UserProtos.PBUserTag getOldPBUserTag(){
        return mPBUserTag;
    }


    public int getIconCount(){
        if(mAdapter == null) return 0;

        return mAdapter.getUsers().size();
    }

    public List<UserProtos.PBUser> getPBUser(){

        if(mAdapter == null) return null;
        return mAdapter.getUsers();
    }

    public UserProtos.PBUserTag getBuilder(String name, String tagId){

        UserProtos.PBUserTag.Builder builder = UserProtos.PBUserTag.newBuilder();

        builder.setName(name);
        builder.setTid(tagId);
        builder.addAllUsers(getPBUser());

        return builder.build();

    }

    public UserProtos.PBUserTag getBuilder(String name){
        return getBuilder(name , mTabId);
    }

    public void setUser(UserProtos.PBUser user, Activity activity) {
        setUser(user, activity, ICON_ORDINARY);
    }

    public void setUser(UserProtos.PBUser user, Activity activity, int type) {

        if(user == null) return ;

        List<UserProtos.PBUser> pbUsers = getListPBUser(null);
        pbUsers.add(user);

        setUsers(pbUsers, activity, type);
    }

    public void setUsers(List<UserProtos.PBUser> users, Activity activity, int type) {

        mAdapter = new FriendIconListAdapter(getContext(), users, activity, type);
        mGridView.setAdapter(mAdapter);
    }


    public void setUsers(List<UserProtos.PBUser> users, Activity activity) {
        setUsers(getListPBUser(users), activity, ICON_ORDINARY);
    }

    public void addUsers(List<UserProtos.PBUser> users) {
        if (mAdapter == null) return;
        mAdapter.addUser(users);
        setMenbertext(users.size());

//        mBuilder.addAllUsers(users);
    }

    public void removeUsers(List<UserProtos.PBUser> users){
        mAdapter.removewUsers(users);
        List<UserProtos.PBUser> newUsers = mAdapter.getUsers();
//
//        mBuilder.clear();
//
//        mBuilder.addAllUsers(newUsers);
    }

    public void saveOldBuilder(){
//        mOldBuilder = UserProtos.PBUserTag.newBuilder(mBuilder.build());
        mOldPbuserLists = new ArrayList<>(mAdapter.getUsers());
    }


    public UserProtos.PBUserTag getPBUserTag(){
        return mPBUserTag;
    }

    //是否修改的标签
    public boolean isAlterIcon(){
        return mIsAlter;
    }

    public void setReFresh(List<UserProtos.PBUser> users, Activity activity, int type) {
        mAdapter.setUser(getListPBUser(users), type);
    }







    public void refresh(){
//        setReFresh(mOldBuilder.getUsersList(), mActivity, FriendIconList.ICON_ORDINARY);
//        mBuilder.clear();
//        mBuilder = UserProtos.PBUserTag.newBuilder(mOldBuilder.build());
//        mOldBuilder.clear();
        setReFresh(mOldPbuserLists , mActivity ,FriendIconList.ICON_ORDINARY);
    }


//    public void addUserToTag() {
//        if (mNewPBUserTag != null)
//            mBuilder.addAllUsers(mNewPBUserTag.getUsersList());
//        mNewPBUserTag = null;
//        setMenbertext(mBuilder.getUsersList().size());
//    }


    private List<UserProtos.PBUser> getListPBUser(List<UserProtos.PBUser> users) {
        if (users == null) return new ArrayList<>();
        return new ArrayList<>(users);
    }


    public void setIconType(int type) {
        if (mAdapter == null) return;
        mAdapter.setIconType(type);
    }

    public void setMenbertext(String text , int num){
        if(mPeopleNum == null)  return;
        mText = text;
        mPeopleNum.setText(text+"("+(mAdapter == null ? 0 : mAdapter.getChildCount())+")");
    }

    public void setMenbertext(int num){
        if(mPeopleNum == null) return;
        mPeopleNum.setText(mText+"("+(mAdapter == null ? 0 : mAdapter.getChildCount())+")");
    }

    /**
     * 设置可以头像的类型,是否可以添加或者删除头像
     */
    public void setDeleteType() {
        setIconType(ICON_ADD_AND_DELETE_BUTTON);
    }


    public void setOnClickItemListener(OnClickItemListener l) {
        if (mAdapter == null) return;

        mL = l;
        mAdapter.setOnClickItemListener(mOnClickItemListener);
    }


    private void startToChooseFriend() {
        UserProtos.PBUserTag.Builder builder =  UserProtos.PBUserTag.newBuilder();
        builder.addAllUsers(mAdapter.getUsers());
        builder.setTid("sa");
        UserProtos.PBUserTag b1 = builder.build();

        Intent intent = new Intent(mActivity, FriendListSelectActivity.class);
        intent.putExtra(TABKEY, new String[]{mTabId, "11"});
        intent.putExtra("b1", b1.toByteArray());
        mActivity.startActivityForResult(intent, 0x11);
    }


    private OnClickItemListener mOnClickItemListener = new OnClickItemListener() {
        @Override
        public void onClickItem(int postion, View view, Object data, int iconType) {

            if (iconType == FriendIconList.OnClickItemListener.INCO_ADD_BUTTON) {
                startToChooseFriend();
            } else if (iconType == FriendIconList.OnClickItemListener.ICON_ORDINARY_BUTTON) {
                //点击对应的头像

            } else if (iconType == FriendIconList.OnClickItemListener.ICON_TOP_DELETE_BUTTON) {
                //点击头像做删除的按钮

//                mBuilder.removeUsers(postion);
                setMenbertext(mAdapter.getUsers().size());
                mIsAlter = true;
            }

            if(mL != null)
                mL.onClickItem(postion , view , data , iconType);
        }
    };


    //把个需要在OnActivityForResult的方法里面被调用
    public boolean startForResult(int resultCode , Intent data){
        if (resultCode == TAG_IS_ALTER) {
            byte[] b = data.getByteArrayExtra(TABKEY);
            try {
                mNewPBUserTag = UserProtos.PBUserTag.parseFrom(b);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
            mIsAlter = true;
            addUsers(mNewPBUserTag.getUsersList());
//            addUserToTag();
            return false;
        }
        return false;
    }


    public interface OnClickItemListener {
        //点击了头像，触发的事件
        public static final int ICON_ORDINARY_BUTTON = 0;
        //点击了删除的按钮
        public static final int ICON_DELETE_BUTTON = 1;
        //点击了添加头像的按钮
        public static final int INCO_ADD_BUTTON = 2;
        //点击了头像头部的删除按钮
        public static final int ICON_TOP_DELETE_BUTTON = 3;

        public void onClickItem(int postion, View view, Object data, int iconType);
    }






}
