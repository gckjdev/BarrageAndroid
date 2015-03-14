package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.inject.Inject;
import com.orange.barrage.android.R;
import com.orange.barrage.android.ui.topic.model.PictureTopicModel;
import com.orange.protocol.message.UserProtos;

import java.util.List;

/**
 * Created by Administrator on 2015/3/13.
 */
public class FriendIconList extends LinearLayout {

    //普通头像
    public static final int ORDINARY_ICON = 0;
    //可以删除的头像
    public static final int ADD_AND_DELETE_ICON = 1;


    FriendIconListAdapter mAdapter;
    private GridView mGridView;

    public FriendIconList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_friends_icon_list , this);
    }


    public void setUsers(List<UserProtos.PBUser> users , Activity activity){
        if(mAdapter == null)
            mAdapter = new FriendIconListAdapter(getContext() ,users , activity);
        if(mGridView == null)
            mGridView = (GridView) findViewById(R.id.friend_icon_gridview);

        mGridView.setAdapter(mAdapter);
    }

    /**
     * 设置可以头像的类型,是否可以添加或者删除头像
     */
    public void setDeleteType(){
        if(mAdapter == null) return;
        mAdapter.setICONType(ADD_AND_DELETE_ICON);
        mAdapter.notifyDataSetChanged();
    }


    public void setOnClickItemListener(OnClickItemListener l){
        if(mAdapter == null) return;
        mAdapter.setOnClickItemListener(l);
    }



    public interface OnClickItemListener{
        public void onClickItem(int postion , View view , Object data);
    }



}
