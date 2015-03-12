package com.orange.barrage.android.user.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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

    final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mHandler.sendEmptyMessage(0);
    }

    private void startActivity(){

        if(mUserManager.hasUser()){
            ActivityIntent.startIntent(this , HomeActivity.class);
        }else{
            ActivityIntent.startIntent(this, LoginHomeWithInviteCodeActivity.class);
        }
        finish();
    }
}
