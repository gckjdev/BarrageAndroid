package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class UserHomeSettingActivity extends BarrageCommonActivity {
    @InjectView(R.id.setting_about_layout)
    private LinearLayout mSettingAboutLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_setting,"系统设置",-1);
        mSettingAboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeSettingActivity.this, UserHomeSettingAboutActivity.class);
            }
        });
    }
}
