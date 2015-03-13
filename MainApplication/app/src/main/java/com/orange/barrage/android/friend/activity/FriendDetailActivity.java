package com.orange.barrage.android.friend.activity;

import android.os.Bundle;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

public class FriendDetailActivity extends BarrageCommonActivity{

    UserProtos.PBUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_friend_detail,"详细资料",-1);
    }
}
