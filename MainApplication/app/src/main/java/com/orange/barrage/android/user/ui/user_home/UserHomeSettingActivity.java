package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeSettingActivity extends BarrageCommonActivity {
    @InjectView(R.id.setting_about_layout)
    private LinearLayout mSettingAboutLayout;

    @InjectView(R.id.setting_style_layout)
    private LinearLayout mSettingStyleLayout;

    @InjectView(R.id.setting_speed_layout)
    private LinearLayout mSettingSpeedLayout;

    @InjectView(R.id.setting_style_textview)
    private TextView mSettingStyleTextView;

    @InjectView(R.id.setting_speed_textview)
    private TextView mSettingSpeedTextView;
    @Inject
    UserManager mUserManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_setting,"系统设置",-1);
        final  UserProtos.PBUser user = mUserManager.getUser();
        mSettingStyleTextView.setText(user.getMemo());
/*
       mSettingSpeedTextView.setText(user.getBSpeed());
*/
        //关于界面
        mSettingAboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeSettingActivity.this, UserHomeSettingAboutActivity.class);
            }
        });
        //风格选择
        mSettingStyleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeSettingActivity.this,UserHomeSettingStyleActivity.class);
            }
        });
        //速度选择
        mSettingSpeedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeSettingActivity.this,UserHomeSettingSpeedActivity.class);
            }
        });
    }
}
