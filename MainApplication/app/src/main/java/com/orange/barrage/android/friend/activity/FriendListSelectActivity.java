package com.orange.barrage.android.friend.activity;

import android.os.Bundle;
import android.view.View;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.friend.ui.FriendListInfoListView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectView;


/**
 * Created by Administrator on 2015/3/20.
 */
public class FriendListSelectActivity extends BarrageCommonActivity {



    @InjectView(R.id.friendListView)
    FriendListInfoListView mFriendListInfoListView;


    @Inject
    FriendManager mFriendManager;



    @Override
    protected void onCreate(Bundle saveBundle){
        super.onCreate(saveBundle , R.layout.activity_friend_list_select_ , R.string.b_friend_select_friend , R.string.b_complete);

        mFriendListInfoListView.setUserInfo();



    }


    @Override
    public void onClickLeft(View v) {
        //返回

    }

    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);
        //确定提交
        UserProtos.PBUser.Builder builder =  UserProtos.PBUser.newBuilder();
        List<UserProtos.PBUser> PBUsers = mFriendListInfoListView.getAddPBUser();



    }
}
