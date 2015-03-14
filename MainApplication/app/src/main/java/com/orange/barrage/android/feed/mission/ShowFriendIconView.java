package com.orange.barrage.android.feed.mission;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.ui.FriendIconItem;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.util.activity.FloatWindow;
import com.orange.protocol.message.UserProtos;

import java.util.List;

/**
 * Created by Administrator on 2015/3/13.
 */
public class ShowFriendIconView {


    private FloatWindow mFloatWindow;
    private Activity mActivity;
    private FriendIconList mFrinedInconList;

    public ShowFriendIconView(Activity activity){
        mActivity = activity;
        initView();
    }

    private void initView(){
        mFloatWindow = mFloatWindow == null ? (new FloatWindow(R.layout.view_popupwindow_friend_list , mActivity)) :mFloatWindow;
    }


    public void show(View parent , List<UserProtos.PBUser> users){

        initView();
        mFloatWindow.show(parent);
        View v  = mFloatWindow.getContextView();
        mFrinedInconList = (FriendIconList) v .findViewById(R.id.friendIconFrinedInconlist);

        mFrinedInconList.setUsers(users , mActivity);
        mFrinedInconList.setDeleteType();

    }




}
