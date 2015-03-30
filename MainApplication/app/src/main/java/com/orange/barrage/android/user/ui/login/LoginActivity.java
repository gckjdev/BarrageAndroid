package com.orange.barrage.android.user.ui.login;

import android.os.Bundle;
import android.view.KeyEvent;

import android.view.View;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;


public class LoginActivity extends BarrageCommonActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState ,R.layout.activity_login , R.string.y_logn , -1);
        mBarrageAndroid.addActivity(this);
    }

    public void onClickQQ(View v){
    }

    public void onClickXinliang(View v){
    }

    public void onClickWeixin(View v){

    }
    public void onClickEmail(View v){
        startActivityPhoneEmail(LoginEditeTextActivtiy.EMAIL_LOGIN);
    }
    public void onClickPhone(View v){
        startActivityPhoneEmail(LoginEditeTextActivtiy.PHONE_LOGIN);
    }

    private void startActivityPhoneEmail(int type){
        ActivityIntent.startIntent(this , LoginEditeTextActivtiy.class, LoginEditeTextActivtiy.Key , type);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            mBarrageAndroid.pushActivity();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClickFinish(View v) {
        mBarrageAndroid.pushActivity();
        super.onClickFinish(v);
    }
}
