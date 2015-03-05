package com.orange.barrage.android.user.ui.invitecode;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import com.google.inject.Inject;
import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.InviteCodeManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.ToastUtil;
import com.orange.protocol.message.UserProtos;

import roboguice.inject.InjectView;

public class InviteCodePassActivity extends BarrageCommonActivity {


    @InjectView(R.id.id)
    EditText mPhoneEdiText;

    @InjectView(R.id.pwd)
    EditText mPwdEdiTet;

    @Inject
    UserMission mUserMission;

    @Inject
    InviteCodeManager mInviteCodeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code_pass);
    }


    public void onClickSend(View v){

        String pwd = mPwdEdiTet.getText().toString();
        String id = mPhoneEdiText.getText().toString();
        if(id == null || id.length() != 11){
            ToastUtil.makeTextShort( R.string.pleaseinput,this);
            return;
        }
        if(pwd == null || pwd.length() == 0){
            ToastUtil.makeTextShort(R.string.y_pleaseinputpwd , this);
            return;
        }

        progresShow();

        String inviteCode = mInviteCodeManager.getCurrentInviteCode();



        mUserMission.regiseterUserByMobile(id , pwd , inviteCode, new UserMissionCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
             if(errorCode == 0 ){
                ActivityIntent.startIntent(InviteCodePassActivity.this , HomeActivity.class);
             }else{
                 ToastUtil.makeTextShort(R.string.y_reginfail,InviteCodePassActivity.this);
             }
              progresClose();
            }
        });

    }






}
