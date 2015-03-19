package com.orange.barrage.android.user.ui.user_home.user_settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.mission.UserMissionCallback;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.view.InputEditText;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeModifyPasswordActivity extends BarrageCommonActivity {
    @InjectView(R.id.input_old_password)
    private InputEditText mInputOldPassword;

    @InjectView(R.id.input_new_password)
    private InputEditText mInputNewPassword;

    @InjectView(R.id.input_confirm_password)
    private InputEditText mInoutConfirmPassword;

    @InjectView(R.id.submit_password)
    private Button mSubmitPassword;

    @InjectView(R.id.old_password_layout)
    private LinearLayout mOldPasswordLayout;
    @Inject
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_modify_password,"修改密码",-1);
        final  UserProtos.PBUser user = mUserManager.getUser();
        boolean isHasPassword=user.hasPassword();
        //假如有密码的情况下
        if (isHasPassword)
        {
            mOldPasswordLayout.setVisibility(View.VISIBLE);
            mSubmitPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(mInputOldPassword.getText().toString())){
                        MessageCenter.postErrorMessage("旧密码不能为空");
                        return ;
                    }
                    if (!(UserManager.encryptPassword(mInputOldPassword.getText().toString())).equals(user.getPassword()))
                    {
                        MessageCenter.postErrorMessage("输入的密码不是原来的旧密码，请输入正确的旧密码");
                        return ;
                    }
                    if (TextUtils.isEmpty(mInputNewPassword.getText().toString())||(TextUtils.isEmpty(mInoutConfirmPassword.getText().toString()))){
                        MessageCenter.postErrorMessage("新密码不能为空");
                        return ;
                    }
                    else if (!mInputNewPassword.getText().toString().equals(mInoutConfirmPassword.getText().toString())){
                        MessageCenter.postErrorMessage("两次输入新密码不一样，请重新输入");
                        return ;
                    }else if (mInputNewPassword.getText().length()>16){
                        MessageCenter.postErrorMessage("密码的长度不能大于16个字符");
                        return ;
                    }
                    else
                    {
                        String password=UserManager.encryptPassword(mInputNewPassword.getText().toString());
                        mUserMission.updateUserPassword(password,new UserMissionCallback() {
                            @Override
                            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                                if (errorCode == 0){
                                    MessageCenter.postSuccessMessage("密码已成功更新啦，赶紧去登陆吧");
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }
        //假如无密码的情况下
        else
        {
            mOldPasswordLayout.setVisibility(View.GONE);
            mSubmitPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(mInputNewPassword.getText().toString())||(TextUtils.isEmpty(mInoutConfirmPassword.getText().toString()))){
                        MessageCenter.postErrorMessage("新密码不能为空");
                        return ;
                    }
                    else if (!mInputNewPassword.getText().toString().equals(mInoutConfirmPassword.getText().toString())){
                        MessageCenter.postErrorMessage("两次输入新密码不一样，请重新输入");
                        return ;
                    }else if (mInputNewPassword.getText().length()>16){
                        MessageCenter.postErrorMessage("密码的长度不能大于16个字符");
                        return ;
                    }
                    else
                    {
                        String password=UserManager.encryptPassword(mInputNewPassword.getText().toString());
                        mUserMission.updateUserPassword(password,new UserMissionCallback() {
                            @Override
                            public void handleMessage(int errorCode, UserProtos.PBUser pbUser) {
                                if (errorCode == 0){
                                    MessageCenter.postSuccessMessage("密码已成功更新啦，赶紧去登陆吧");
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
