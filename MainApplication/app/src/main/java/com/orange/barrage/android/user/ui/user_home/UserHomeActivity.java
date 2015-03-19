package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeActivity extends BarrageCommonActivity {
    @InjectView(R.id.user_home_layout)
    private RelativeLayout mUserHomeView;

    @InjectView(R.id.user_home_myfriend_layout)
    private LinearLayout mUserHomeMyfriend;

    @InjectView(R.id.user_home_invitemyfriend)
    private LinearLayout mUserHomeInviteMyfriend;

    @InjectView(R.id.user_home_mytag)
    private LinearLayout mUsrHomeMyTag;

    @InjectView(R.id.user_home_setting)
    private LinearLayout mUserHomeSetting;

    @InjectView(R.id.friend_home_avatarview)
    private UserAvatarView userAvatarImageView;

    @InjectView(R.id.friend_home_nick)
    private TextView mFriendHomeview;

    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home,"我的主页",-1);
        mUserHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeActivity.this,UserHomeModifyActivity.class);
            }
        });
        UserProtos.PBUser user = mUserManager.getUser();
        userAvatarImageView.loadUser(user);
        mFriendHomeview.setText(user.getNick());
    }
}
