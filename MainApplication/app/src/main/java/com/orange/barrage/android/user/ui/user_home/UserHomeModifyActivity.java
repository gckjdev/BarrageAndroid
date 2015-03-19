package com.orange.barrage.android.user.ui.user_home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.user.ui.user_home.user_settings.UserNickEditTextActivity;
import com.orange.barrage.android.user.ui.user_home.user_settings.UserSignatureEditTextActivity;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class UserHomeModifyActivity extends BarrageCommonActivity {
    @InjectView(R.id.nick_layout)
    private RelativeLayout mNickLayout;

    @InjectView(R.id.signature_layout)
    private RelativeLayout mSignatureLayout;

    @InjectView(R.id.user_nick_layout)
    private LinearLayout mLayoutModifyNick;

    @InjectView(R.id.user_avatarview)
    private UserAvatarView mUserAvatarImageView;

    @InjectView(R.id.user_modify_nick)
    private TextView mUserModifyNick;

    @InjectView(R.id.user_modify_signature)
    private TextView mUserModifySignature;

    @InjectView(R.id.user_location)
    private TextView mUserLocation;

    @InjectView(R.id.user_modify_gender)
    private TextView mUserModifyGender;

    @InjectView(R.id.user_modify_password)
    private LinearLayout mUserModifyPassword;

    @InjectView(R.id.user_select_gender)
    private LinearLayout mUserSelectGender;

    @Inject
    UserManager mUserManager;

    public boolean panduan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_user_home_modify,"个人资料",-1);


        //用户只有get方法和has方法，没有set方法
       final  UserProtos.PBUser user = mUserManager.getUser();
        panduan=user.getGender();
        mUserAvatarImageView.loadUser(user);
        mUserModifyNick.setText(user.getNick());
        mUserModifySignature.setText(user.getSignature());

        if (TextUtils.isEmpty(user.getLocation())){
            mUserLocation.setText("什么也没有");
        }
        else{
            mUserLocation.setText(user.getLocation());
        }

        if (panduan){
            mUserModifyGender.setText("男");
        }
        else{
            mUserModifyGender.setText("女");
        }

        //修改昵称
        mNickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserNickEditTextActivity.start(UserHomeModifyActivity.this,
                        UserNickEditTextActivity.class,
                        mUserManager.getUser().getNick());
            }
        });

        //修改签名
        mSignatureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSignatureEditTextActivity.start(UserHomeModifyActivity.this,
                        UserSignatureEditTextActivity.class,
                        mUserManager.getUser().getSignature());
            }
        });
        //修改密码
        mUserModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityIntent.startIntent(UserHomeModifyActivity.this,UserHomeModifyPasswordActivity.class);
            }
        });

        //修改性别
        mUserSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.activity_user_select_gender,null);
               // ActivityIntent.startIntent(UserHomeModifyActivity.this,UserSelectGenderActivity.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserHomeModifyActivity.this);
                builder.setView(view);
                final Dialog dialog = builder.create();
                dialog.show();

                final LinearLayout mSelectMaleLayout = (LinearLayout)view.findViewById(R.id.select_male);
                final LinearLayout mSelectFemaleLayout = (LinearLayout)view.findViewById(R.id.select_female);
                final ImageView mSelectMaleView = (ImageView)view.findViewById(R.id.select_male_view);
                final ImageView mSelectFemaleView = (ImageView)view.findViewById(R.id.select_female_view);


                //如果是男的话
                if (panduan)
                {
                    mSelectMaleView.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
                    mSelectFemaleView.setImageDrawable(null);
                }

                //否则是女的
                else
                {
                    mSelectMaleView.setImageDrawable(null);
                    mSelectFemaleView.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
                }
                mSelectMaleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mSelectMaleView.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
                        mSelectFemaleView.setImageDrawable(null);
                        mUserModifyGender.setText("男");
                        panduan=true;

                    }
                });

                mSelectFemaleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mSelectFemaleView.setImageDrawable(getResources().getDrawable(R.drawable.selectgender));
                        mSelectMaleView.setImageDrawable(null);
                        mUserModifyGender.setText("女");
                        panduan=false;
                    }
                });
            }
        });


        // TODO set data here
    }

}
