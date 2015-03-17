package com.orange.barrage.android.feed.mission;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.util.activity.FloatWindow;
import com.orange.barrage.android.util.view.LayoutDrawIconBackground;
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
        mFloatWindow = mFloatWindow == null ?
                (new FloatWindow(R.layout.view_popupwindow_friend_list , mActivity , ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT , Gravity.CENTER|Gravity.TOP))
                :mFloatWindow;
    }





    public void show(View parent , List<UserProtos.PBUser> users){

        initView();
        mFloatWindow.show(parent);
        View v  = mFloatWindow.getContextView();
        mFrinedInconList = (FriendIconList) v .findViewById(R.id.friendIconFrinedInconlist);

        LayoutDrawIconBackground.Params params = new LayoutDrawIconBackground.Params();

        params.marginLeft  = 20;
        params.marginRight = 20;

        new LayoutDrawIconBackground().setParams(params).setWhitTriangleRadioRoundFrectBg(parent, mFrinedInconList);

        mFrinedInconList.setUsers(users , mActivity);

    }




}
