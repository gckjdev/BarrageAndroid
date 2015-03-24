package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconList extends GridView {

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


    FriendIconListAdapter mAdapter;
//    private GridView mGridView;

    public FriendIconList(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initView();
    }

//    private void initView(){
//        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_friends_icon_list , null);
//
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT);
//
//        addView(v , params);
//
//    }

    private void initView(){



    }

    public void setUsers(List<UserProtos.PBUser>users , Activity activity , int type){

        if(mAdapter == null)
            mAdapter = new FriendIconListAdapter(getContext() ,getListPBUser(users) , activity , type);
//        if(mGridView == null)
//            mGridView = (GridView) findViewById(R.id.friend_icon_gridview);
        setAdapter(mAdapter);
    }





    public void setUsers(List<UserProtos.PBUser> users , Activity activity){
        setUsers(users , activity , ICON_ORDINARY);
    }

    public void addUsers(List<UserProtos.PBUser> users){
        if(mAdapter == null) new Throwable("Adapter == null");
        mAdapter.addUser(users);
    }


    public void setReFresh(List<UserProtos.PBUser>users , Activity activity , int type){
        mAdapter.setUser(getListPBUser(users) ,type);
    }

    private List<UserProtos.PBUser> getListPBUser(List<UserProtos.PBUser>users){
        List<UserProtos.PBUser> userList = null;

        if(users == null) users = new ArrayList<>();

        if(users != null)
            userList = new ArrayList<>(users);

        return userList;
    }


    public void setIconType(int type){
        if(mAdapter == null) return ;
        mAdapter.setIconType(type);
    }



    /**
     * 设置可以头像的类型,是否可以添加或者删除头像
     */
    public void setDeleteType(){
        setIconType(ICON_ADD_AND_DELETE_BUTTON);
    }


    public void setOnClickItemListener(OnClickItemListener l){
        if(mAdapter == null) return;
        mAdapter.setOnClickItemListener(l);
    }



    public interface OnClickItemListener{
        //点击了头像，触发的事件
        public static final int ICON_ORDINARY_BUTTON = 0;
        //点击了删除的按钮
        public static final int ICON_DELETE_BUTTON = 1;
        //点击了添加头像的按钮
        public  static final int INCO_ADD_BUTTON = 2;
        //点击了头像头部的删除按钮
        public static final int ICON_TOP_DELETE_BUTTON = 3;

        public void onClickItem(int postion , View view , Object data , int iconType);
    }



}
