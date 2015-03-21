package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeSettingSpeedActivity extends BarrageCommonActivity {
    @InjectView(R.id.setting_speed_normallayout)
    private LinearLayout mSettingSpeedNormal;

    @InjectView(R.id.setting_speed_superhightlayout)
    private LinearLayout mSettingSPeedSuperHight;

    @InjectView(R.id.setting_speed_hightlayout)
    private LinearLayout mSettingSpeedHight;

    @InjectView(R.id.setting_speed_lowerlayout)
    private LinearLayout mSettingSpeedLower;

    @InjectView(R.id.setting_speed_superlowerlayout)
    private LinearLayout mSettingSPeedSuperLower;

    @InjectView(R.id.speed_select_normal)
    private ImageView mSpeedNormal;

    @InjectView(R.id.speed_select_superhight)
    private ImageView mSpeedSuperHight;

    @InjectView(R.id.speed_select_hight)
    private ImageView mSpeedHight;

    @InjectView(R.id.speed_select_lower)
    private ImageView mSpeedLower;

    @InjectView(R.id.speed_select_superlower)
    private ImageView mSpeedSuperLower;

    @Inject
    UserManager mUserManager;
    //这个方法是默认全部不显示图片
    private void showImageView()
    {
        mSpeedNormal.setImageDrawable(null);
        mSpeedSuperHight.setImageDrawable(null);
        mSpeedHight.setImageDrawable(null);
        mSpeedLower.setImageDrawable(null);
        mSpeedSuperLower.setImageDrawable(null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_home_setting_speed, "请选择速度", R.string.user_setting_save);
        final  UserProtos.PBUser user = mUserManager.getUser();

        //可以用getStyle()取得风格
        mSettingSpeedNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mSpeedNormal.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingSPeedSuperHight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mSpeedSuperHight.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingSpeedHight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mSpeedHight.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingSpeedLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mSpeedLower.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingSPeedSuperLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mSpeedSuperLower.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
    }
}
