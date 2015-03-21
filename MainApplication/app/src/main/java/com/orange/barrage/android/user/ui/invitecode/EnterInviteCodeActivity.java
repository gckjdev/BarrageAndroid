package com.orange.barrage.android.user.ui.invitecode;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.InviteCodeManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.ToastUtil;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class EnterInviteCodeActivity extends BarrageCommonActivity {

    @Inject
    UserMission mUserMission;

    @InjectView(R.id.codeEditText)
    EditText mCodeEditText;

    @Inject
    InviteCodeManager mInviteCodeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState ,R.layout.activity_enter_invite_code  , "填写邀请码" , -  1 );
        mBarrageAndroid.addActivity(this);
        mCodeEditText.setText(mInviteCodeManager.getCurrentInviteCode());
    }



    /**
     * 提交邀请码
     * @param v
     */
    public void onClickSendCode(View v){

        String code = mCodeEditText.getText().toString();

        if(code == null || code.length() == 0){
            ToastUtil.makeTextShort(R.string.codeisnull , this);
            return ;
        }
        showProgress("");
        mInviteCodeManager.setCurrentInviteCode(code);

        mUserMission.verifyInviteCode(code, new UserMissionCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
             Ln.d("verfiy invite code, error code=" + errorCode);
                if (errorCode == 0) {
                    // success, goto register
                    ActivityIntent.startIntent(EnterInviteCodeActivity.this, InviteCodePassActivity.class);
                }
                dismissProgress();
            }
        });


    }




    /**
     * 发送照片
     * @param v
     */
    public void onClickSendPhoto(View v){

    }


    @Override
    public void onClickFinish(View v) {

        mBarrageAndroid.pushActivity();

        super.onClickFinish(v);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //后退键中Activity出栈
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mBarrageAndroid.pushActivity();
        }

        return super.onKeyDown(keyCode, event);
    }
}
