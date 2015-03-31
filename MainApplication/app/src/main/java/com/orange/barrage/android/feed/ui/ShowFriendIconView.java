package com.orange.barrage.android.feed.ui;

import android.app.Activity;
import android.graphics.Color;
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

    }

    private boolean initView(){
        boolean is = false;
        mFloatWindow = (is = mFloatWindow == null) ?
                (new FloatWindow(R.layout.view_popupwindow_friend_list , mActivity , ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT , Gravity.TOP))
                :mFloatWindow;

        return is;
    }


    public void show(View parent , List<UserProtos.PBUser> users){

        boolean is = initView();
        mFloatWindow.show(parent);

        if(!is) return;
        View v  = mFloatWindow.getContextView();
        mFrinedInconList = (FriendIconList) v .findViewById(R.id.friendIconFrinedInconlist);

        v.findViewById(R.id.chengyuan).setVisibility(View.GONE);

        LayoutDrawIconBackground.Params params = new LayoutDrawIconBackground.Params();

        params.marginLeft  = 10;
        params.marginRight = 10;
        params.bgColor = Color.WHITE;
        new LayoutDrawIconBackground().setParams(params).setWhitTriangleRadioRoundFrectListenerBg(parent, mFrinedInconList);

        mFrinedInconList.setUsers(users , mActivity);
        mFrinedInconList.setIconType(FriendIconList.ICON_ORDINARY);
        mFrinedInconList.setOnClickItemListener(mOnClickItemListener);
    }

    private FriendIconList.OnClickItemListener mOnClickItemListener = new FriendIconList.OnClickItemListener() {
        @Override
        public boolean onClickItem(int postion, View view, Object data, int iconType) {
            if(iconType == ICON_DELETE_BUTTON){

            }
            return false;
        }
    };




}
