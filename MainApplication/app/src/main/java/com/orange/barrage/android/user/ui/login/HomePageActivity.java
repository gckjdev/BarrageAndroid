package com.orange.barrage.android.user.ui.login;

import android.os.Bundle;
import android.os.Handler;

import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import javax.inject.Inject;

/**
 * Created by youjiannuo on 2015/3/10.
 */
public class HomePageActivity extends BarrageCommonActivity {

    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState, R.layout.activity_home_page, "", -1);

        /**
         * 延迟一秒进入主页面
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity();
            }
        }, 1000);

    }

    private void startActivity() {

        if (mUserManager.hasUser()) {
            ActivityIntent.startIntent(this, HomeActivity.class);
        } else {
            ActivityIntent.startIntent(this, LoginHomeWithInviteCodeActivity.class);
        }
        finish();
    }
}
