package com.orange.barrage.android.user.ui.invitecode;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import com.orange.barrage.android.util.activity.MessageCenter;
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
        super.onCreate(savedInstanceState, R.layout.activity_registered, R.string.y_zhuce, -1);

    }


    /**
     * 下拉设置
     */
    public void onClickSF(View v) {

        if (v.getTag() == null || v.getTag().equals("c")) {
            mLayout.setVisibility(View.VISIBLE);
            v.setTag("O");
            ((ImageButton) v).setImageResource(R.drawable.y_zhuce_shouhui);
            showMenuAnimation();
        } else {
            v.setTag("c");
            ((ImageButton) v).setImageResource(R.drawable.y_zhuche_xiala);
            closeMenuAnimation();
        }

    }


    private void closeMenuAnimation() {

        for (int i = 0; i < mLayout.getChildCount(); i++) {
            closeChildAnimation(mLayout.getChildAt(i), i);
        }

    }


    private void showMenuAnimation() {

        int j = 0;
        for (int i = mLayout.getChildCount() - 1; i >= 0; i--) {
            showChildAnimation(mLayout.getChildAt(i), j++);
        }

    }

    private void closeChildAnimation(View v, int i) {


        Animation alphaAnimation = new TranslateAnimation(0, 0, 0, -(v.getHeight() + v.getY()));
        alphaAnimation.setFillAfter(true);
        setChildAnimation(alphaAnimation, i, v);

        if (i == mLayout.getChildCount() - 1) {
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

    private void showChildAnimation(View v, int i) {

        Animation alphaAnimation = new TranslateAnimation(0, 0, -(v.getHeight() + v.getY()), 0);
        setChildAnimation(alphaAnimation, i, v);
    }

    void setChildAnimation(Animation alphaAnimation, int i, View v) {
        if (v.getVisibility() == View.GONE) return;
        alphaAnimation.setDuration(500);
        alphaAnimation.setStartOffset(100 * i);
        v.startAnimation(alphaAnimation);
    }


    @Override
    protected void initView(){
        super.initView();

        if (PHONE == mType) {
            mLayout.getChildAt(3).setVisibility(View.VISIBLE);
            mLayout.getChildAt(4).setVisibility(View.GONE);
            mPhoneEdiText.setHint(R.string.y_inputphone);
            mPhoneEdiText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            mLayout.getChildAt(3).setVisibility(View.GONE);
            mLayout.getChildAt(4).setVisibility(View.VISIBLE);
            mPhoneEdiText.setHint(R.string.y_pleaseinputEmail);
            mPhoneEdiText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSend(v);
            }
        });

    }

    public void onClickSend(View v) {

        String pwd = mPwdEdiTet.getText().toString();
        String id = mPhoneEdiText.getText().toString();
        if (mType == PHONE) {
            if (StringUtil.isEmpty(id)) {
                ToastUtil.makeTextShort(R.string.pleaseinput, this);
                return;
            } else if (!StringUtil.isPhoneNumberValid(id)) {
                ToastUtil.makeTextShort(R.string.pleaseinput, this);
                return;
            }
        } else if (mType == EMAIL) {
            if (StringUtil.isEmpty(id)) {
                ToastUtil.makeTextShort(R.string.y_pleaseinoutEmailRight, this);
                return;
            } else if (!StringUtil.isEmail(id)) {
                ToastUtil.makeTextShort(R.string.y_pleaseinoutEmailRight, this);
                return;
            }
        }
        if (pwd == null || pwd.length() == 0) {
            ToastUtil.makeTextShort(R.string.y_pleaseinputpwd, this);
            return;
        }

        showProgress("");

        String inviteCode = mInviteCodeManager.getCurrentInviteCode();

        pwd = UserManager.encryptPassword(pwd);

        if (mType == PHONE) {

            mUserMission.regiseterUserByMobile(id, pwd, inviteCode, mUserMissionCalssback);
        } else {
            mUserMission.regiseterUserByEmail(id, pwd, inviteCode, mUserMissionCalssback);
        }
    }


    UserMissionCallback mUserMissionCalssback = new UserMissionCallback() {
        @Override
        public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
            dealResult(errorCode);
        }
    };


    private void dealResult(int errorCode) {
        if (errorCode == 0) {
            ActivityIntent.startIntent(InviteCodePassActivity.this, HomeActivity.class);
            mBarrageAndroid.clearActivity();
            finish();
        } else {
            ToastUtil.makeTextShort(R.string.y_reginfail, InviteCodePassActivity.this);
        }

        dismissProgress();
    }

    public void onClickQQ(View v) {

    }

    public void onClickXinliang(View v) {

    }

    public void onClickWeixin(View v) {

    }

    public void onClickEmail(View v) {
        if (mType == EMAIL) return;
        mType = EMAIL;
        initView();
    }

    public void onClickPhone(View v) {
        if (mType == PHONE) return;
        mType = PHONE;
        initView();
    }

}
