package com.orange.barrage.android.user.ui.user_home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class UserHomeModifyActivity extends BarrageCommonActivity {

    @InjectView(R.id.usermodifyNicker)
    private LinearLayout mLayoutmodifyNicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_modify,"修改个人资料",-1);
        mLayoutmodifyNicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeModifyActivity.this,UserModifyUserForresult.class);
            }
        });
    }


}