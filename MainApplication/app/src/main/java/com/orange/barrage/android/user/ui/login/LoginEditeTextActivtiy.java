package com.orange.barrage.android.user.ui.login;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.ToastUtil;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/5.
 */

public class LoginEditeTextActivtiy extends BarrageCommonActivity {

    /*邮箱登录*/
    public static final int EMAIL_LOGIN = 1;
    /*手机登录*/
    public static final int PHONE_LOGIN = 2;

    public static final String Key = "y";

    /*当前是什么状态登录*/
    private int mType = PHONE_LOGIN;

    @InjectView(R.id.sendbutton)
    Button mSendButton;

    @InjectView(R.id.id)
    EditText mIdEditText;

    @InjectView(R.id.pwd)
    EditText mPwdEditText;

   /* @Inject
    UserInfoManager mUserInfoManager;
*/

    @Inject
    UserMission mUserMission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login_edittext, R.string.y_logn, -1);
        initView();

    }

    private void initView(){

        mSendButton.setText(R.string.y_logn);
        setEidtText(getIntentInt(Key , PHONE_LOGIN ));

    }


    protected void setEidtText(int type){
        if(type == EMAIL_LOGIN){
            mType = EMAIL_LOGIN;
            mIdEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            mIdEditText.setHint(R.string.y_pleaseinputEmail);
            // id = mUserInfoManager.getEmailString();
        }//else //id = mUserInfoManager.getPhoneString();

        mIdEditText.setText("");
    }


    /**
     * 登录
     * @param v
     */
    public void onClickSend(View v ){

        String id = mIdEditText.getText().toString();
        String pwd = mPwdEditText.getText().toString();

        boolean isPhone = mType == PHONE_LOGIN;

        if(StringUtil.isEmpty(id)){
            ToastUtil.makeTextShort( !isPhone?R.string.y_pleaseinputEmail : R.string.y_inputphone , this);
            return;
        }else if(isPhone && !StringUtil.isPhoneNumberValid(id)){
            ToastUtil.makeTextShort( R.string.pleaseinput , this);
            return;
        }else if(!isPhone && !StringUtil.isEmail(id)){
            ToastUtil.makeTextShort(R.string.y_pleaseinoutEmailRight, this);
            return;
        }
        else if(StringUtil.isEmpty(pwd)){
            ToastUtil.makeTextShort( R.string.y_pleaseinputpwd , this);
            return;
        }

        pwd = UserManager.encryptPassword(pwd);

        showProgress("");

        if(isPhone) {
        //    mUserInfoManager.setPhoneString(id);
            mUserMission.loginUserByMobile(id , pwd , mUserMissionClassback);
        }else {
        //    mUserInfoManager.setEmailString(id);
            mUserMission.loginUserByEmail(id ,pwd , mUserMissionClassback );
        }

    }

    private  UserMissionCallback mUserMissionClassback = new UserMissionCallback() {
        @Override
        public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
            dismissProgress();
            dealLoginResult(errorCode);
        }
    };


    private void dealLoginResult(int errorCode ){

        if(errorCode == 0){
            ActivityIntent.startIntent(this, HomeActivity.class);
            mBarrageAndroid.clearActivity();
            finish();
        }

    }




}
