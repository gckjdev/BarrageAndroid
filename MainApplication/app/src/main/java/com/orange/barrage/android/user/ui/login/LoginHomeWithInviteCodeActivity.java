package com.orange.barrage.android.user.ui.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import  com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.user.ui.invitecode.EnterInviteCodeActivity;
import com.orange.barrage.android.user.ui.invitecode.InviteCodePassActivity;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

public class LoginHomeWithInviteCodeActivity extends BarrageCommonActivity implements View.OnClickListener {


    @InjectView(R.id.load)
    TextView mLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home_with_invite_code);
        initView();
    }

    private void initView(){
        mLoad.setText(Html.fromHtml("<u>"+getString(R.string.y_first_load)+"</u>"));
        mLoad.setOnClickListener(this);
    }

    /**
     * 跳转注册码界面
     * @param v
     */
    public void onClickZhuMa(View v){
        ActivityIntent.startIntent(this , EnterInviteCodeActivity.class);
    }

    /**
     * 直接跳转到登录界面
     * @param v
     */
    public void onClick(View v){
        ActivityIntent.startIntent(this , HomeActivity.class);
    }


}
