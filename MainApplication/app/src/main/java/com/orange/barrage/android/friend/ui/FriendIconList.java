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

import java.util.List;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconList extends LinearLayout {

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
    private GridView mGridView;

    public FriendIconList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_friends_icon_list , this);
    }

    public void setUsers(List<UserProtos.PBUser>users , Activity activity , int type){
        if(mAdapter == null)
            mAdapter = new FriendIconListAdapter(getContext() ,users , activity , type);
        if(mGridView == null)
            mGridView = (GridView) findViewById(R.id.friend_icon_gridview);
        mGridView.setAdapter(mAdapter);
    }


    public void setUsers(List<UserProtos.PBUser> users , Activity activity){
       setUsers(users , activity , ICON_ORDINARY);
    }

    public void setIconType(int type){
        if(mAdapter == null) return ;
        mAdapter.setIconType(type);
        mAdapter.notifyDataSetChanged();
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

        public static final int ICON_ORDINARY = 0;

        public static final int ICON_DELETE = 1;

        public  static final int INCO_ADD = 2;

        public void onClickItem(int postion , View view , Object data , int iconType);
    }



}
