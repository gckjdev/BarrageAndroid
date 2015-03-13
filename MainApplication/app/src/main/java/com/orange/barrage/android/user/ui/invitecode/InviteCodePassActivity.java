package com.orange.barrage.android.user.ui.invitecode;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.google.inject.Inject;
import com.orange.barrage.android.R;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.user.mission.UserMission;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.InviteCodeManager;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.ToastUtil;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.UserProtos;

import roboguice.inject.InjectView;

public class InviteCodePassActivity extends BarrageCommonActivity {


    @InjectView(R.id.id)
    EditText mPhoneEdiText;

    @InjectView(R.id.pwd)
    EditText mPwdEdiTet;

    @InjectView(R.id.zhuche_other_id)
    LinearLayout mLayout;

    @Inject
    UserMission mUserMission;

    @Inject
    InviteCodeManager mInviteCodeManager;

    private static final int PHONE = 1;

    private static final int EMAIL = 2;

    /*当前登录的类型*/
    private int mType = PHONE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState ,R.layout.activity_invite_code_pass , R.string.y_zhuce , -1  );
        initView();

    }


    public void onClickSF(View v){

        if(v.getTag() == null || v.getTag().equals("c")){
            ((ImageButton)v).setImageResource(R.drawable.y_zhuce_shouhui);
            v.setTag("k");
            mLayout.setVisibility(View.VISIBLE);
        }else{
            ((ImageButton)v).setImageResource(R.drawable.y_zhuche_xiala);
            v.setTag("c");
            mLayout.setVisibility(View.GONE);
        }

    }


    private void initView(){

        if(PHONE == mType){
            mLayout.getChildAt(3).setVisibility(View.VISIBLE);
            mLayout.getChildAt(4).setVisibility(View.GONE);
            mPhoneEdiText.setHint(R.string.y_inputphone);
            mPhoneEdiText.setInputType(InputType.TYPE_CLASS_PHONE);
        }else{
            mLayout.getChildAt(3).setVisibility(View.GONE);
            mLayout.getChildAt(4).setVisibility(View.VISIBLE);
            mPhoneEdiText.setHint(R.string.y_pleaseinputEmail);
            mPhoneEdiText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

    }

    public void onClickSend(View v){

        String pwd = mPwdEdiTet.getText().toString();
        String id = mPhoneEdiText.getText().toString();
        if(mType == PHONE){
            if(StringUtil.isEmpty(id)){
                ToastUtil.makeTextShort(R.string.pleaseinput, this);
                return;
            }
            else if(!StringUtil.isPhoneNumberValid(id)) {
                ToastUtil.makeTextShort(R.string.pleaseinput, this);
                return;
            }
        }else if(mType == EMAIL) {
            if(StringUtil.isEmpty(id)){
                ToastUtil.makeTextShort(R.string.y_pleaseinoutEmailRight,this);
                return;
            }
            else if(!StringUtil.isEmail(id)){
                ToastUtil.makeTextShort(R.string.y_pleaseinoutEmailRight , this);
                return;
            }
        }
        if(pwd == null || pwd.length() == 0){
            ToastUtil.makeTextShort(R.string.y_pleaseinputpwd , this);
            return;
        }

        showProgress("");

        String inviteCode = mInviteCodeManager.getCurrentInviteCode();

        pwd = UserManager.encryptPassword(pwd);

        if(mType == PHONE) {

            mUserMission.regiseterUserByMobile(id, pwd, inviteCode,  mUserMissionCalssback);
        }else {
            mUserMission.regiseterUserByEmail(id , pwd , inviteCode , mUserMissionCalssback);
        }
    }


    UserMissionCallback mUserMissionCalssback = new UserMissionCallback() {
        @Override
        public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
            dealResult(errorCode);
        }
    };


    private void dealResult(int errorCode){
        if (errorCode == 0) {
            ActivityIntent.startIntent(InviteCodePassActivity.this, HomeActivity.class);
            mBarrageAndroid.clearActivity();
            finish();
        } else {
            ToastUtil.makeTextShort(R.string.y_reginfail, InviteCodePassActivity.this);
        }
    }

    public void onClickQQ(View v){

    }
    public void onClickXinliang(View v){

    }
    public void onClickWeixin(View v){

    }
    public void onClickEmail(View v){
        if(mType == EMAIL) return;
        mType = EMAIL;
        initView();
    }
    public void onClickPhone(View v){
        if(mType == PHONE) return;
        mType = PHONE;
        initView();
    }

}
