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

public class UserHomeSettingStyleActivity extends BarrageCommonActivity {
    @InjectView(R.id.setting_style_mocalayout)
    private LinearLayout mSettingStyleMoca;

    @InjectView(R.id.setting_style_tanhenglayout)
    private LinearLayout mSettingStyletanheng;

    @InjectView(R.id.setting_style_yunsulayout)
    private LinearLayout mSettingStyleYunsu;

    @InjectView(R.id.setting_style_jiasulayout)
    private LinearLayout mSettingStyleJiasu;

    @InjectView(R.id.setting_style_jiansulayout)
    private LinearLayout mSettingStyleJiansu;

    @InjectView(R.id.setting_style_jiasujiansulayout)
    private LinearLayout mSettingStyleJiasuJiansu;

    @InjectView(R.id.style_select_moca)
    private ImageView mStyleMoca;

    @InjectView(R.id.style_select_tanhuang)
    private ImageView mStyleTanhuang;

    @InjectView(R.id.style_select_yunsu)
    private ImageView mStyleYunsu;

    @InjectView(R.id.style_select_jiasu)
    private ImageView mStyleJiasu;

    @InjectView(R.id.style_select_jiasujiansu)
    private ImageView mStyleJiasuJiansu;

    @InjectView(R.id.style_select_jiansu)
    private ImageView mStyleJiansu;

    @Inject
    UserManager mUserManager;
    //刚开始的时候全部不显示图片
    private void showImageView()
    {
        mStyleMoca.setImageDrawable(null);
        mStyleTanhuang.setImageDrawable(null);
        mStyleYunsu.setImageDrawable(null);
        mStyleJiasu.setImageDrawable(null);
        mStyleJiasuJiansu.setImageDrawable(null);
        mStyleJiansu.setImageDrawable(null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_setting_style,"请选择风格",R.string.user_setting_save);
        final  UserProtos.PBUser user = mUserManager.getUser();
        //user.getSpeed()取得速度
        mSettingStyleMoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mStyleMoca.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingStyletanheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mStyleTanhuang.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingStyleYunsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mStyleYunsu.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingStyleJiasu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mStyleJiasu.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingStyleJiansu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mStyleJiansu.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
        mSettingStyleJiasuJiansu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageView();
                mStyleJiasuJiansu.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
            }
        });
    }
}
